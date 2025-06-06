package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
	@NotBlank(message = "제목을 입력해주세요.")
	@Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
	private String title;

	@Schema(description = "공지 내용", example = "크루 모집 관련해서 공지합니다.")
	@NotBlank(message = "내용을 입력해주세요.")
	@Size(max = 2000, message = "내용은 2000자 이하로 입력해주세요.")
	private String content;

	@Schema(description = "첨부 파일 경로 (S3)", example = "release/announcement/sample.jpg (임시)")
	@Size(max = 1000, message = "첨부파일 경로는 1000자 이하로 입력해주세요.")
	private String attachPath;
}
