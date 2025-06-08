package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant;

import lombok.Getter;

@Getter
public enum FeedOrder {
	LATEST,
	OLDEST,
	NAME_ASC,
	NAME_DESC,
	MOST_LIKED,
	MOST_COMMENTED,
	RELEVANT,
}