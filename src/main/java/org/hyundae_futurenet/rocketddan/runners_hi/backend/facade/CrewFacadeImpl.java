package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;
import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.CrewException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequestStatus;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewJoinRequestSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewMemberSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewJoinRequestListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew.CrewJoinRequestMapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew.CrewMapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew.CrewMemberMapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew.CrewJoinRequestService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew.CrewMemberService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew.CrewService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file.CloudFrontFileUtil;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file.S3FileUtil;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.mail.GoogleMailUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrewFacadeImpl implements CrewFacade {

	private final CrewService crewService;

	private final CrewMemberService crewMemberService;

	private final CrewJoinRequestService crewJoinRequestService;

	private final S3FileUtil s3FileUtil;

	private final CrewMapper crewMapper;

	private final CrewMemberMapper crewMemberMapper;

	private final CloudFrontFileUtil cloudFrontFileUtil;

	private final int VALID_SECONDS = 60 * 10;

	private final CrewJoinRequestMapper crewJoinRequestMapper;

	private final GoogleMailUtil googleMailUtil;

	@Override
	@Transactional
	public long insertCrew(long loginMemberId, CrewCreateRequest crewCreateRequest, MultipartFile multipartFile) {
		// 이미 크루 멤버인 경우 생성 불가
		if (crewMemberMapper.isCrewMember(loginMemberId)) {
			throw new CrewException(ErrorCode.ALREADY_EXIST_CREW_MEMBER);
		}
		// 크루 이름 중복 검사
		if (crewMapper.existsByName(crewCreateRequest.getCrewName())) {
			throw new CrewException(ErrorCode.DUPLICATED_CREW_NAME);
		}
		// 크루 먼저 생성
		crewService.insertCrew(loginMemberId, crewCreateRequest);
		long crewId = crewCreateRequest.getCrewId();

		// 크루 프로필 이미지 설정
		String profilePath = null;
		// 업로드한 프로필 사진이 존재하면
		if (multipartFile != null && !multipartFile.isEmpty()) {
			profilePath = s3FileUtil.uploadCrewProfile(multipartFile, crewId);
			crewService.updateCrewProfilePath(crewId, profilePath);
			log.info("Profile path: {}", profilePath);
		}

		// 크루에 리더 추가
		crewMemberService.insertCrewMember(loginMemberId, loginMemberId, crewId, true);
		return crewId;
	}

	@Override
	@Transactional
	public void updateCrew(
		long loginMemberId,
		long crewId,
		CrewUpdateRequest crewUpdateRequest,
		MultipartFile multipartFile) {
		// 크루 존재 여부 검증
		checkCrewExisted(crewId);

		// 크루 이름 중복 검사
		if (crewMapper.existsByName(crewUpdateRequest.crewName())) {
			throw new CrewException(ErrorCode.DUPLICATED_CREW_NAME);
		}

		// 크루장만 변경 가능
		checkCrewLeader(loginMemberId, crewId);

		String profilePath = crewUpdateRequest.profilePath();
		// 프로필 새로 업로드하는 경우
		if (multipartFile != null && !multipartFile.isEmpty()) {
			profilePath = s3FileUtil.uploadCrewProfile(multipartFile, crewId);
		}

		crewService.updateCrew(loginMemberId, crewId, crewUpdateRequest, profilePath);
	}

	@Override
	@Transactional
	public void deleteCrew(long loginMemberId, long crewId) {
		// 크루 존재 여부 검증
		checkCrewExisted(crewId);
		// 크루장만 삭제 가능
		checkCrewLeader(loginMemberId, crewId);
		crewService.deleteCrew(loginMemberId, crewId);
	}

	@Override
	@Transactional
	public CrewDetailResponse selectCrewByCrewId(long loginMemberId, long crewId) {
		// 크루 존재 여부 검증
		CrewDetailResponse result = crewService.selectCrewByCrewId(loginMemberId, crewId)
			.orElseThrow(() -> new CrewException(ErrorCode.NOT_FOUND_CREW));

		Optional<CrewMemberDetailResponse> memberDetail = crewMemberService.selectCrewMember(loginMemberId, crewId);

		// 멤버 여부 및 역할 정보, 가입 요청 여부 저장
		if (memberDetail.isPresent()) {
			result = result.toBuilder()
				.isLeader(memberDetail.get().isLeader())
				.isMember(true)
				.hasRequestedJoin(crewJoinRequestMapper.existsCrewJoinRequestByMemberIdAndCrewId(loginMemberId, crewId))
				.profilePath(getFilePath(result.getProfilePath()))
				.build();
		} else {
			result = result.toBuilder()
				.isLeader(false)
				.isMember(false)
				.hasRequestedJoin(crewJoinRequestMapper.existsCrewJoinRequestByMemberIdAndCrewId(loginMemberId, crewId))
				.profilePath(getFilePath(result.getProfilePath()))
				.build();
		}
		return result;
	}

	@Override
	@Transactional
	public List<CrewListResponse> selectCrewsByFilter(long loginMemberId, CrewSearchFilter crewSearchFilter) {

		List<CrewListResponse> result = crewService.selectCrewsByFilter(loginMemberId, crewSearchFilter);
		return result.stream()
			.map(rep -> rep.toBuilder().profilePath(getFilePath(rep.getProfilePath())).build()).toList();
	}

	@Override
	@Transactional
	public void insertCrewJoinRequest(long loginMemberId, long crewId, CrewJoinRequest crewJoinRequest) {
		// 크루 존재 여부 검증
		checkCrewExisted(crewId);

		// 이미 다른 크루에 요청 대기 중인 상태이면 더 이상 요청 불가
		if (crewJoinRequestMapper.existsCrewJoinRequestByMemberId(loginMemberId)) {
			throw new CrewException(ErrorCode.ALREADY_EXIST_CREW_JOIN_REQUEST);
		}

		// 이미 크루 멤버인 경우 생성 불가
		if (crewMemberMapper.isCrewMember(loginMemberId)) {
			throw new CrewException(ErrorCode.ALREADY_EXIST_CREW_MEMBER);
		}

		crewJoinRequestService.insertCrewJoinRequest(loginMemberId, crewId, crewJoinRequest);

		// TODO: 크루장에게 가입 요청 메일 보내기
		// 해당 크루(crewId)의 크루장의 이메일 가져오기
		// String email = crewMemberService.searchCrewLeadersEmail(crewId);
		// googleMailUtil.sendCrewJoinRequestMail(email);
	}

	@Override
	@Transactional
	public void updateCrewJoinRequest(long loginMemberId,
		long crewId,
		long crewJoinRequestId,
		CrewJoinRequestStatus status) {

		// 크루 존재 여부 검증
		checkCrewExisted(crewId);

		// 크루장만 변경 가능
		checkCrewLeader(loginMemberId, crewId);

		crewJoinRequestService.updateCrewJoinRequest(loginMemberId, crewJoinRequestId, status);

		// TODO: 크루원에게 가입 요청 메일 보내기
		// 해당 가입요청 id(crewJoinRequestId)에 해당하는 멤버의 이메일 가져오기
		// String email = crewJoinRequestService.getMemberEmail(crewJoinRequestId);
		// googleMailUtil.sendCrewJoinRequestMail(email);
	}

	@Override
	public void deleteCrewJoinRequest(long loginMemberId, long crewId) {

		if (!crewJoinRequestMapper.existsCrewJoinRequestByMemberIdAndCrewId(loginMemberId, crewId)) {
			throw new CrewException(ErrorCode.NOT_FOUND_CREW_JOIN_REQUEST);
		}

		crewJoinRequestService.deleteCrewJoinRequest(loginMemberId, crewId);
	}

	@Override
	@Transactional
	public List<CrewJoinRequestListResponse> selectCrewJoinRequestsByStatus(
		long loginMemberId,
		long crewId,
		CrewJoinRequestSearchFilter filter) {

		// 크루장만 조회 가능
		checkCrewLeader(loginMemberId, crewId);
		return crewJoinRequestService
			.selectCrewJoinRequestsByStatus(crewId, filter)
			.stream()
			.map(rep -> rep.toBuilder().profilePath(getFilePath(rep.getProfilePath())).build()).toList();
	}

	@Override
	public boolean isExistNextPage(CrewJoinRequestSearchFilter filter) {

		return crewJoinRequestService.isExistNextPage(filter);
	}

	@Override
	@Transactional
	public void insertCrewMember(long loginMemberId, long memberId, long crewId) {
		// 크루장만 가능
		checkCrewLeader(loginMemberId, crewId);
		crewMemberService.insertCrewMember(loginMemberId, memberId, crewId, false);
	}

	@Override
	@Transactional
	public void deleteCrewMember(long loginMemberId, long crewId) {
		// 크루 존재 여부 검증
		checkCrewExisted(crewId);

		// 크루 회원만 가능
		CrewMemberDetailResponse result = crewMemberService.selectCrewMember(loginMemberId, crewId)
			.orElseThrow(() -> new CrewException(ErrorCode.NOT_FOUND_CREW));

		// 크루장이면 탈퇴 불가능
		if (result.isLeader()) {
			throw new IllegalArgumentException("크루장은 탈퇴할 수 없습니다.");
		}

		crewMemberService.deleteCrewMember(loginMemberId, result.crewMemberId());
	}

	@Override
	@Transactional
	public void forcedRemovalCrewMember(long loginMemberId, long crewId, long crewMemberId) {

		// 크루장만 가능
		checkCrewLeader(loginMemberId, crewId);

		crewMemberService.deleteCrewMember(loginMemberId, crewMemberId);
	}

	@Override
	@Transactional
	public List<CrewMemberListResponse> selectCrewMembers(
		long loginMemberId,
		long crewId,
		CrewMemberSearchFilter crewMemberSearchFilter) {

		return crewMemberService
			.selectCrewMembers(loginMemberId, crewId, crewMemberSearchFilter)
			.stream()
			.map(rep -> rep.toBuilder().profilePath(getFilePath(rep.getProfilePath())).build()).toList();
	}

	@Override
	@Transactional
	public void updateCrewMemberIsLeader(long loginMemberId, long crewId, long crewMemberId) {
		// 로그인 유저가 크루장인지 점검
		CrewMemberDetailResponse leaderInfo = crewMemberService.selectCrewMember(loginMemberId, crewId).get();
		if (leaderInfo == null || !leaderInfo.isLeader()) {
			throw new IllegalArgumentException("크루장이 아닙니다.");
		}

		// 권한을 넘기려는 멤버가 일반 크루원인지 점검
		if (!crewMemberMapper.isCrewMemberByCrewMemberIdAndCrewId(crewMemberId, crewId)) {
			throw new IllegalArgumentException("일반 크루원이 아닙니다.");
		}

		// 크루장 -> 일반 멤버
		crewMemberService.updateCrewMemberIsLeader(loginMemberId, leaderInfo.crewMemberId(), false);
		// 일반 멤버 -> 크루장
		crewMemberService.updateCrewMemberIsLeader(loginMemberId, crewMemberId, true);

	}

	@Override
	@Transactional
	public List<CrewListResponse> recommendCrewsByRegion(int perPage, String region) {

		return crewService.recommendCrewsByRegion(perPage, region)
			.stream()
			.map(rep -> rep.toBuilder().profilePath(getFilePath(rep.getProfilePath())).build()).toList();
	}

	@Override
	public Long selectMyCrew(long loginMemberId) {

		return crewService.selectMyCrew(loginMemberId);
	}

	@Override
	public boolean existsByCrewName(String crewName) {

		return crewService.existsByCrewName(crewName);
	}

	// 크루장이 아닌 경우 예외 처리
	private void checkCrewLeader(long loginMemberId, long crewId) {

		if (!crewMemberService.isLeader(loginMemberId, crewId)) {
			throw new IllegalArgumentException("크루장이 아닙니다.");
		}
	}

	private void checkCrewExisted(long crewId) {
		// 크루 존재 여부 검증
		if (!crewMapper.existsByCrewId(crewId)) {
			throw new CrewException(ErrorCode.NOT_FOUND_CREW);
		}
	}

	private String getFilePath(String filePath) {

		if (filePath != null) {
			filePath = cloudFrontFileUtil.generateSignedUrl(filePath, VALID_SECONDS);
		}
		return filePath;
	}
}

