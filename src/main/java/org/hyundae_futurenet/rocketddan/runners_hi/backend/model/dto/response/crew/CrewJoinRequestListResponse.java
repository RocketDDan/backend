package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

public record CrewJoinListResponse(
	long crewJoinRequestId,
	long memberId,
	String nickname,
	String email,
	String profilePath,
	String requestDate,
	String requestMessage,
	String status
) {

}
