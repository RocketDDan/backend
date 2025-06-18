package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.FeedListSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.feed.FeedListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.feed.FeedListResponse.CommentThumbnail;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.feed.FeedListResponse.FeedFileUrl;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file.CloudFrontFileUtil;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedListResponseConverter {

	private final CloudFrontFileUtil cloudFrontFileUtil;

	public FeedListResponse toDto(FeedListSource source) {
		// 피드 파일
		List<FeedFileUrl> feedFileUrls = source.getFeedFilePathList().stream()
			.map(file -> {
				String feedFileUrl = cloudFrontFileUtil.generateSignedUrl(file.getFilePath(), 60 * 10);
				// log.info("feed file path: {} \nfeed file url: {}", file.getFilePath(), feedFileUrl);
				return new FeedFileUrl(
					file.getOrder(),
					feedFileUrl
				);
			})
			.collect(Collectors.toList());
		// 작성자 프로필 url
		String writerProfileUrl = source.getWriterProfilePath() != null
			? cloudFrontFileUtil.generateSignedUrl(source.getWriterProfilePath(), 60 * 10)
			: null;
		// 댓글 작성자 프로필 url
		List<CommentThumbnail> commentThumbnails = source.getCommentList().stream()
			.map(comment -> {
				String commentWriterProfileUrl = comment.getWriterProfilePath() != null
					? cloudFrontFileUtil.generateSignedUrl(comment.getWriterProfilePath(), 60 * 10)
					: null;
				// log.info("comment writer profile path: {} \ncomment writer profile url: {}",
				// 	comment.getWriterProfilePath(), commentWriterProfileUrl);
				return new CommentThumbnail(
					comment.getCommentId(),
					commentWriterProfileUrl,
					comment.getWriterNickname(),
					comment.getContent());
			})
			.collect(Collectors.toList());

		return new FeedListResponse(
			source.getFeedId(),
			source.getContent(),
			source.getWriterId(),
			source.getWriterNickname(),
			writerProfileUrl,
			source.getViewCount(),
			source.isMine(),
			source.isLike(),
			source.getType(),
			source.getCommentCount(),
			source.getLikeCount(),
			source.getLat(),
			source.getLng(),
			source.getCreatedAt(),
			feedFileUrls,
			commentThumbnails
		);
	}
}