package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnnouncementListResponse {

	@Schema(description = "공지 제목", example = "시스템 점검 안내")
	private String title;

	@Schema(description = "공지 대상 크루명 (없을 경우 null)", example = "강남러너스")
	private String crewName;

	@Schema(description = "공지 ID", example = "101")
	private Long announcementId;

	@Schema(description = "공지 작성 일시 (yyyy-MM-dd HH:mm:ss)", example = "2025-06-17 09:00:00")
	private String createdAt;

	@Schema(description = "공지 작성자", example = "1")
	private Long createdBy;
}
