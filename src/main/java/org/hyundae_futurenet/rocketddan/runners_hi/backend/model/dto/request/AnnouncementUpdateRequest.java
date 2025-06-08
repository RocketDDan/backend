package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "공지사항 수정 요청")
public class AnnouncementUpdateRequest {

	@Schema(description = "공지 제목", example = "수정된 제목")
	@Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
	private String title;

	@Schema(description = "공지 내용", example = "수정된 내용")
	@Size(max = 2000, message = "내용은 2000자 이하로 입력해주세요.")
	private String content;

	@Schema(description = "첨부 파일 경로 리스트 (최대 3개)", example = "[\"release/announcement/sample1.jpg\", \"release/announcement/sample2.pdf\"]")
	@Size(max = 3, message = "첨부파일은 최대 3개까지 등록할 수 있습니다.")
	private List<String> attachPaths;

}
