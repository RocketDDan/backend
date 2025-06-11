package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Schema(description = "피드 목록 응답")
public class FeedListResponse {

	@Schema(description = "피드 ID", example = "1")
	private long feedId;

	@Schema(description = "피드 본문 내용", example = "오늘도 열심히 달렸습니다!")
	private String content;

	@Schema(description = "피드 작성자의 ID", example = "42")
	private long writerId;

	@Schema(description = "피드 작성자의 닉네임", example = "러너짱")
	private String writerNickname;

	@Schema(description = "피드 작성자의 프로필 이미지 URL", example = "https://cdn.example.com/profile.jpg")
	private String writerProfileUrl;

	@Schema(description = "현재 로그인한 사용자의 피드인지 여부", example = "true")
	private boolean isMine;

	@Schema(description = "댓글 수", example = "5")
	private int commentCount;

	@Schema(description = "위도", example = "37.55")
	private Double lat;

	@Schema(description = "경도", example = "127.05")
	private Double lng;

	@Schema(description = "피드 생성 시각 (YYYY-MM-DD HH:MM:SS 형식)", example = "2025-06-04 12:34:56")
	private String createdAt;

	@Schema(description = "피드에 첨부된 파일 URL 목록")
	private List<FeedFileUrl> feedFileUrlList;

	@Schema(description = "댓글 썸네일 목록 (최대 3개)")
	private List<CommentThumbnail> commentList;

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@Schema(description = "피드 첨부 파일 정보")
	public static class FeedFileUrl {

		@Schema(description = "파일 정렬 순서", example = "1")
		private int order;

		@Schema(description = "파일 URL", example = "https://cdn.example.com/feed/file1.jpg")
		private String fileUrl;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	@Schema(description = "댓글 썸네일 정보")
	public static class CommentThumbnail {

		@Schema(description = "댓글 작성자 프로필 이미지 URL", example = "https://cdn.example.com/profile.jpg")
		private String writerProfileUrl;

		@Schema(description = "댓글 작성자 닉네임", example = "러너짱")
		private String writerNickname;

		@Schema(description = "댓글 내용", example = "정말 멋져요!")
		private String content;
	}
}