package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.crew;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CrewJoinRequestSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewJoinRequestSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewJoinRequestListResponse;

@Mapper
public interface CrewJoinRequestMapper {

	boolean existsCrewJoinRequestByMemberIdAndCrewId(@Param("memberId") long memberId, @Param("crewId") long crewId);

	// 가입 요청 존재 여부
	boolean existsCrewJoinRequest(@Param("crewJoinRequestId") long crewJoinRequestId);

	// 가입 요청 생성
	void insertCrewJoinRequest(
		@Param("crewId") long crewId,
		@Param("loginMemberId") long loginMemberId,
		@Param("message") String requestMessage);

	// 가입 요청 상태 변경
	void updateCrewJoinRequest(
		@Param("loginMemberId") long loginMemberId,
		@Param("status") String status,
		@Param("crewJoinRequestId") long crewJoinRequestId);

	// 가입 요청 삭제
	void deleteCrewJoinRequest(
		@Param("memberId") long memberId,
		@Param("crewId") long crewId
	);

	// 가입 요청 상태별 목록 조회
	List<CrewJoinRequestListResponse> selectCrewJoinRequestsByStatus(
		@Param("req") CrewJoinRequestSearchFilter requestSearchFilter,
		@Param("status") String status);

	// 가입 요청 정보 조회
	Optional<CrewJoinRequestSource> selectCrewJoinRequestByCrewJoinRequestId(
		@Param("crewJoinRequestId") long crewJoinRequestId);
}
