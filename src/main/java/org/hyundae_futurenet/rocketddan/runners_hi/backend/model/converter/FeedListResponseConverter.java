package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.FeedListSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedListResponse.CommentThumbnail;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedListResponse.FeedFileUrl;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file.CloudFrontFileUtil;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedListResponseConverter {

	private final CloudFrontFileUtil cloudFrontFileUtil;

	public FeedListResponse toDto(FeedListSource source) {
		// 피드 파일
		List<FeedFileUrl> feedFileUrls = source.getFeedFilePathList().stream()
			.map(file -> new FeedFileUrl(
				file.getOrder(),
				cloudFrontFileUtil.generateSignedUrl(file.getFilePath(), 60 * 10)))
			.collect(Collectors.toList());
		// 작성자 프로필 url
		String writerProfileUrl = cloudFrontFileUtil.generateSignedUrl(source.getWriterProfilePath(), 60 * 10);
		// 댓글 작성자 프로필 url
		List<CommentThumbnail> commentThumbnails = source.getCommentList().stream()
			.map(comment -> new CommentThumbnail(
				cloudFrontFileUtil.generateSignedUrl(comment.getWriterProfilePath(), 60 * 10),
				comment.getWriterNickname(),
				comment.getContent()))
			.collect(Collectors.toList());

		return new FeedListResponse(
			source.getFeedId(),
			source.getContent(),
			source.getWriterId(),
			source.getWriterNickname(),
			writerProfileUrl,
			source.isMine(),
			source.getCommentCount(),
			source.getCreatedAt(),
			feedFileUrls,
			commentThumbnails
		);
	}
}