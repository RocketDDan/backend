package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminFeedListResponse {

	private List<AdminFeedResponse> feeds; // 피드 목록

	private int totalCount;

}
