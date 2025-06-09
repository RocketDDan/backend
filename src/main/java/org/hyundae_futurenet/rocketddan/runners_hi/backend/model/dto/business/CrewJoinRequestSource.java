package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business;

public record CrewJoinRequestSource(
	Long crewJoinRequestId,
	Long memberId,
	Long crewId
) {

}
