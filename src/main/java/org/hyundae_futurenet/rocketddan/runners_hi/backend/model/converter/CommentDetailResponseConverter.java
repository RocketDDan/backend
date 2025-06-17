package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.converter;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CommentDetailSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.feed.CommentDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file.CloudFrontFileUtil;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentDetailResponseConverter {

	private final CloudFrontFileUtil cloudFrontFileUtil;

	public CommentDetailResponse toDto(CommentDetailSource source) {

		String writerProfileUrl = source.getWriterProfilePath() != null
			? cloudFrontFileUtil.generateSignedUrl(source.getWriterProfilePath(), 60 * 10)
			: null;

		return new CommentDetailResponse(
			source.getCommentId(),
			source.getContent(),
			source.getWriterId(),
			source.getWriterNickname(),
			writerProfileUrl,
			source.getCreatedAt(),
			source.getUpdatedAt(),
			source.isUpdated(),
			source.isMine());
	}
}
