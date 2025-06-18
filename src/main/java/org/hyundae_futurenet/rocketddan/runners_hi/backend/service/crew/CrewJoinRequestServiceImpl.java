package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.CrewException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CrewJoinRequestSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequestStatus;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewJoinRequestSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewJoinRequestListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew.CrewJoinRequestMapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew.CrewMemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CrewJoinRequestServiceImpl implements CrewJoinRequestService {

	private final CrewJoinRequestMapper crewJoinRequestMapper;

	private final CrewMemberMapper crewMemberMapper;

	@Override
	public void insertCrewJoinRequest(long loginMemberId, long crewId, CrewJoinRequest crewJoinRequest) {

		log.info("CrewJoinRequestService :: insert loginMemberId: {} crewId: {}", loginMemberId, crewId);

		String message = null;
		if (crewJoinRequest != null && crewJoinRequest.requestMessage() != null) {
			message = crewJoinRequest.requestMessage();
		}

		// 이미 가입 요청 이력이 존재하면 불가(REQUEST만)
		if (crewJoinRequestMapper.existsCrewJoinRequestByMemberIdAndCrewId(loginMemberId, crewId)) {
			throw new CrewException(ErrorCode.ALREADY_EXIST_CREW_JOIN_REQUEST);
		}
		crewJoinRequestMapper.insertCrewJoinRequest(crewId, loginMemberId, message);
	}

	@Override
	public void updateCrewJoinRequest(
		long loginMemberId,
		long crewJoinRequestId,
		CrewJoinRequestStatus crewJoinRequestStatus) {

		String status = getStatus(crewJoinRequestStatus);

		log.info("CrewJoinRequestService :: update crewJoinRequestId: {} status: {}", crewJoinRequestId, status);

		CrewJoinRequestSource source = crewJoinRequestMapper.selectCrewJoinRequestByCrewJoinRequestId(crewJoinRequestId)
			.orElseThrow(() -> new CrewException(ErrorCode.NOT_FOUND_CREW_JOIN_REQUEST));

		crewJoinRequestMapper.updateCrewJoinRequest(loginMemberId, status, crewJoinRequestId);

		// 승인 요청인 경우 크루원에 추가
		if (crewJoinRequestStatus.equals(CrewJoinRequestStatus.ACCEPT)) {
			crewMemberMapper.insertCrewMember(loginMemberId, source.memberId(), source.crewId(), false);
		}
	}

	@Override
	public List<CrewJoinRequestListResponse> selectCrewJoinRequestsByStatus(
		long crewId,
		CrewJoinRequestSearchFilter filter) {

		String status = getStatus(filter.getStatus());
		int limit = filter.getPerPage();
		int offset = (filter.getPage() - 1) * limit;
		log.info("CrewJoinRequestService :: selectCrewJoinRequestsByStatus, status: {}", status);

		return crewJoinRequestMapper.selectCrewJoinRequestsByStatus(crewId, filter, status, offset, limit);
	}

	@Override
	public int totalCount(long crewId, String status, String nickname) {

		int total = crewJoinRequestMapper.selectTotalCount(crewId, status, nickname);
		log.info("CrewJoinRequestService :: totalCount = {}", total);
		return total;
	}

	@Override
	public void deleteCrewJoinRequest(long loginMemberId, long crewId) {

		log.info("CrewJoinRequestService :: deleteCrewJoinRequest, crewId = {}", crewId);
		crewJoinRequestMapper.deleteCrewJoinRequest(loginMemberId, crewId);
	}

	@Override
	public String selectEmailByCrewJoinRequestId(long crewJoinRequestId) {

		log.info("CrewJoinRequestService :: selectEmailByCrewJoinRequestId");
		return crewJoinRequestMapper.selectEmailByCrewJoinRequestId(crewJoinRequestId);
	}

	// Status String으로 반환
	private static String getStatus(CrewJoinRequestStatus crewJoinRequestStatus) {

		String status = null;
		if (crewJoinRequestStatus != null) {
			status = crewJoinRequestStatus.name();
		}
		return status;
	}
}
