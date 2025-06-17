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
public class AnnouncementListResult {

	@Schema(description = "공지 목록")
	private List<AnnouncementListResponse> announcements;

	@Schema(description = "전체 공지 개수", example = "27")
	private int totalCount;
}