package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request;

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
@Schema(description = "공지사항 등록 요청")
public class AnnouncementCreateRequest {

	@Schema(description = "공지 제목", example = "크루 관련 공자")
	private String title;

	@Schema(description = "공지 내용", example = "크루 모집 관련해서 공지합니다.")
	private String content;

	@Schema(description = "첨부 파일 경로 (S3)", example = "release/announcement/sample.jpg (임시)")
	private String attachPath;
}
