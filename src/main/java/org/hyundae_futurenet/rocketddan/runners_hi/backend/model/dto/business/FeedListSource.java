package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FeedListSource {

	private long feedId;

	private String content;

	private List<FeedFilePath> feedFilePathList;

	private long writerId;

	private String writerNickname;

	private String writerProfilePath; // S3의 경로로 저장됨

	private boolean isMine;

	private int commentCount;

	private List<CommentThumbnailRaw> commentList;

	private String createdAt;

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

		private String writerProfilePath;

		private String writerNickname;

		private String content;
	}
}