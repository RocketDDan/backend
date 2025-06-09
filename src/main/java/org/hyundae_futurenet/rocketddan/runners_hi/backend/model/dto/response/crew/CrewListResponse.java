package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

public record CrewListResponse(
	long crewId,
	String crewName,
	String crewAddress,
	String crewRegion,
	String profilePath,
	int totalMemberCnt
) {

}
