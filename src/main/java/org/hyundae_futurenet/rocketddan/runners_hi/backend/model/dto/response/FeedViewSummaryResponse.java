package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FeedViewSummaryResponse {

	private int totalViews;

	private int uniqueVisitors;
}
