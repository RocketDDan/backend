package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;
import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.exception.AlreadyExistsException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.exception.NotFoundException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew.CrewMapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew.CrewMemberService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew.CrewService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file.S3FileUtil;
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

	private final S3FileUtil s3FileUtil;

	private final CrewMapper crewMapper;

	@Override
	@Transactional
	public long insertCrew(long loginMemberId, CrewCreateRequest crewCreateRequest, MultipartFile multipartFile) {
		// 크루 이름 중복 검사
		if (crewMapper.existsByName(crewCreateRequest.crewName())) {
			throw new AlreadyExistsException("이미 존재하는 크루명입니다.");
		}
		// 크루 먼저 생성
		long crewId = crewService.insertCrew(loginMemberId, crewCreateRequest);

		// 크루 프로필 이미지 설정
		String profilePath = null;
		if (multipartFile != null && !multipartFile.isEmpty()) {
			profilePath = s3FileUtil.uploadCrewProfile(multipartFile, crewId);
			crewService.updateCrewProfilePath(crewId, profilePath);
			log.info("Profile path: {}", profilePath);
		}
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
		if (!crewMapper.existsByCrewId(crewId)) {
			throw new NotFoundException("존재하지 않는 크루입니다.");
		}

		// 크루 이름 중복 검사
		if (crewUpdateRequest.crewName() != null && crewMapper.existsByName(crewUpdateRequest.crewName())) {
			throw new AlreadyExistsException("이미 존재하는 크루명입니다.");
		}

		// 크루장만 변경 가능
		if (!crewMemberService.isLeader(loginMemberId, crewId)) {
			throw new IllegalArgumentException("크루장만 변경 가능합니다.");
		}

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
		if (!crewMapper.existsByCrewId(crewId)) {
			throw new NotFoundException("존재하지 않는 크루입니다.");
		}
		// 크루장만 삭제 가능
		if (!crewMemberService.isLeader(loginMemberId, crewId)) {
			throw new IllegalArgumentException("크루장만 삭제 가능합니다.");
		}
		crewService.deleteCrew(loginMemberId, crewId);
	}

	@Override
	public CrewDetailResponse selectCrewByCrewId(long loginMemberId, long crewId) {

		CrewDetailResponse result = crewService.selectCrewByCrewId(loginMemberId, crewId)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 크루입니다."));

		Optional<CrewMemberDetailResponse> memberDetail = crewMemberService.selectCrewMember(loginMemberId, crewId);
		if (memberDetail.isPresent()) {
			result.setLeader(memberDetail.get().isLeader());
			result.setMember(true);
		} else {
			result.setLeader(false);
			result.setMember(false);
		}

		return result;
	}

	@Override
	public List<CrewListResponse> selectCrewsByFilter(long loginMemberId, CrewSearchFilter crewSearchFilter) {

		if (crewSearchFilter == null) {
			throw new IllegalArgumentException("조회 옵션은 필수입니다.");
		}
		return crewService.selectCrewsByFilter(loginMemberId, crewSearchFilter);
	}
}

