package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.exception.AlreadyExistsException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.exception.NotFoundException;
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
			throw new AlreadyExistsException("이미 대기중인 가입 요청 이력이 존재합니다.");
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
			.orElseThrow(() -> new NotFoundException("존재하지 않는 가입 요청입니다."));

		crewJoinRequestMapper.updateCrewJoinRequest(loginMemberId, status, crewJoinRequestId);

		// 승인 요청인 경우 크루원에 추가
		if (crewJoinRequestStatus.equals(CrewJoinRequestStatus.ACCEPT)) {
			crewMemberMapper.insertCrewMember(loginMemberId, source.memberId(), source.crewId());
		}
	}

	@Override
	public List<CrewJoinRequestListResponse> selectCrewJoinRequestsByStatus(
		long crewId,
		CrewJoinRequestSearchFilter requestSearchFilter) {

		String status = getStatus(requestSearchFilter.getStatus());

		log.info("CrewJoinRequestService :: selectCrewJoinRequestsByStatus, status: {}", status);

		return crewJoinRequestMapper.selectCrewJoinRequestsByStatus(requestSearchFilter, status);
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
