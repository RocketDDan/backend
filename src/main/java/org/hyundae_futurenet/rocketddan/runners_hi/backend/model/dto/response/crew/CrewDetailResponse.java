package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

public record CrewResponse(
	long crewId,
	String crewName,
	String crewAddress,
	String crewRegion,
	String profilePath,
	String introduce,
	boolean isLeader,
	boolean isMember,
	int totalMemberCnt
) {

}
