package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FeedListResponse {

	private long feedId;

	private String content;

	private String filePath;
}
