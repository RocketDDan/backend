package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
// @AllArgsConstructor // -> 이게 있으면 MyBatis가 생성자 자동 매핑을 시도
@ToString
public class FeedListSource {

	private long feedId;

	private String content;

	private long writerId;

	private String writerNickname;

	private String writerProfilePath;

	private long viewCount;

	private boolean isMine;

	private boolean isLike;

	private int commentCount;

	private int likeCount;

	private Double lat;

	private Double lng;

	private String createdAt;

	private List<FeedFilePath> feedFilePathList;

	private List<CommentThumbnailRaw> commentList;

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	public static class FeedFilePath {

		private int order;

		private String filePath; // CDN 변환 전 파일 경로 (예: S3 Key)
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@ToString
	public static class CommentThumbnailRaw {

		private long commentId;

		private String writerProfilePath;

		private String writerNickname;

		private String content;
	}
}