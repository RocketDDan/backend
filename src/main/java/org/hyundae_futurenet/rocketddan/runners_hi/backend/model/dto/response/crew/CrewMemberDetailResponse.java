package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

public record CrewMember(
	long crewMemberId,
	int isLeader,
	String createdAt
) {

}
