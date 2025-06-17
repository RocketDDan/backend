package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FeedHourlyViewResponse {

	@Schema(description = "조회 시간 (00~23시, 2자리 문자열)", example = "14")
	private String viewHour;

	@Schema(description = "해당 시간대의 클릭 수", example = "27")
	private int views;

}
