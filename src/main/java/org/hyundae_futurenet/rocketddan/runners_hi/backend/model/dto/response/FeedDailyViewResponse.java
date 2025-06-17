package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FeedDailyViewResponse {

	@Schema(description = "조회 날짜 (YYYY-MM-DD 형식)", example = "2025-06-17")
	private String viewDate;

	@Schema(description = "해당 날짜의 클릭 수", example = "124")
	private int views;
}
