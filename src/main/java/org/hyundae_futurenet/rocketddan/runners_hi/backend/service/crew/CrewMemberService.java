package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.crew;

import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberDetailResponse;

public interface CrewMemberService {

	// 크루장 여부 조회
	boolean isLeader(long memberId, long crewId);

	// 멤버 여부 조회
	boolean isCrewMember(long memberId, long crewId);

	// 멤버 정보 조회
	Optional<CrewMemberDetailResponse> selectCrewMember(long memberId, long crewId);
}
