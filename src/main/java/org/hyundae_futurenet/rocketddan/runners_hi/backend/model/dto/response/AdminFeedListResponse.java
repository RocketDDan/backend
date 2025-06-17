package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminFeedListResponse {

	@Schema(description = "관리자에서 보는 전체 피드 목록")
	private List<AdminFeedResponse> feeds;

	@Schema(description = "전체 피드 수")
	private int totalCount;

}
