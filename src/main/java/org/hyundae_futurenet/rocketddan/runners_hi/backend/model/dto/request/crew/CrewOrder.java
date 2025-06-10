package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew;

import lombok.Getter;

@Getter
public enum CrewOrder {
	LATEST, // 최신순
	OLDEST, // 오래된순
	MEMBER_CNT, // 멤버수 순
	NAME_ASCENDING,
	NAME_DESCENDING
}
