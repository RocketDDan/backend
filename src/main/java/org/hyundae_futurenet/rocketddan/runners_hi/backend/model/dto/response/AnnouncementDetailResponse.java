package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDetailResponse {

	@Schema(description = "공지 ID", example = "101")
	private Long announcementId;

	@Schema(description = "공지 작성자 ID", example = "3")
	private Long createdBy;

	@Schema(description = "공지 제목", example = "시스템 점검 안내")
	private String title;

	@Schema(description = "공지 내용", example = "6월 30일 01:00~03:00에 시스템 점검이 예정되어 있습니다.")
	private String content;

	@Schema(description = "공지 생성 일시 (yyyy-MM-dd HH:mm:ss)", example = "2025-06-17 09:15:00")
	private String createdAt;

	@Schema(description = "공지 수정 일시 (yyyy-MM-dd HH:mm:ss)", example = "2025-06-18 14:30:00")
	private String updatedAt;

	@Schema(description = "공지 작성자 닉네임", example = "운영팀")
	private String nickname;

	@Schema(description = "첨부파일 경로 목록", example = "[\"https://cdn.site.com/notice/abc.pdf\"]")
	private List<String> attachPaths;

	@Schema(description = "첨부파일 이미지 확장자 판별", example = "true")
	private List<Boolean> attachIsImageList;
}
