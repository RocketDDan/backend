package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CrewJoinRequestSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequestStatus;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewJoinRequestSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewJoinRequestListResponse;

public interface CrewJoinRequestService {

	// 가입 요청
	void insertCrewJoinRequest(long loginMemberId, long crewId, CrewJoinRequest crewJoinRequest);

	// 가입 요청 상태 변경
	void updateCrewJoinRequest(long loginMemberId, long crewJoinRequestId, CrewJoinRequestStatus status);

	// 가입 요청 목록 조회
	List<CrewJoinRequestListResponse> selectCrewJoinRequestsByStatus(
		long crewId,
		CrewJoinRequestSearchFilter filter);

	// 다음 페이지 존재 여부 조회
	int totalCount(long crewId, String status, String nickname);

	// 가입 요청 삭제
	void deleteCrewJoinRequest(long loginMemberId, long crewId);

	// 가입 요청자의 이메일 가져오기
	String selectEmailByCrewJoinRequestId(long crewJoinRequestId);

	// 나의 가입 요청 조회
	CrewJoinRequestSource selectCrewJoinRequestByMemberId(long crewMemberId);
}
