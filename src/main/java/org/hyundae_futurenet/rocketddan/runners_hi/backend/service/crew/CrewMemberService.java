package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.List;
import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewMemberSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberListResponse;

public interface CrewMemberService {

	// 크루장 여부 조회
	boolean isLeader(long memberId, long crewId);

	// 멤버 여부 조회
	boolean isCrewMember(long memberId, long crewId);

	// 멤버 정보 조회
	Optional<CrewMemberDetailResponse> selectCrewMember(long memberId, long crewId);

	// 크루 멤버 생성
	void insertCrewMember(long loginMemberId, long memberId, long crewId, boolean isLeader);

	// 크루 멤버 탈퇴
	void deleteCrewMember(long loginMemberId, long crewMemberId);

	// 크루 멤버 목록 조회
	List<CrewMemberListResponse> selectCrewMembers(
		long loginMemberId,
		long crewId,
		CrewMemberSearchFilter crewMemberSearchFilter);

	// 크루장 여부 변경
	void updateCrewMemberIsLeader(long loginMemberId, long crewMemberId, boolean isLeader);
}

