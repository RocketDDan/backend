package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

public record CrewMemberDetailResponse(
	long crewMemberId,
	long memberId,
	boolean isLeader,
	String createdAt
) {

}
