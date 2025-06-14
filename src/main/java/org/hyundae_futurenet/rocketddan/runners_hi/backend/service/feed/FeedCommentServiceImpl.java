package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CommentDetailSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed.FeedCommentMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedCommentServiceImpl implements FeedCommentService {

	private final FeedCommentMapper feedCommentMapper;

	@Override
	public void deleteAll(long feedId) {

		feedCommentMapper.deleteAll(feedId);
	}

	@Override
	public void register(long loginMemberId, long feedId, String comment) {

		feedCommentMapper.insert(loginMemberId, feedId, comment);
	}

	@Override
	public void assertCommentExists(long loginMemberId, long feedId, long commentId) {

		if (!feedCommentMapper.validateCommentExistence(loginMemberId, feedId, commentId)) {
			throw new IllegalArgumentException("해당 로그인한 유저와 피드 id에 해당하는 댓글이 없습니다.");
		}
	}

	@Override
	public void update(long commentId, String newComment) {

		feedCommentMapper.update(commentId, newComment);
	}

	@Override
	public void delete(long commentId) {

		feedCommentMapper.delete(commentId);
	}

	@Override
	public List<CommentDetailSource> searchCommentList(long loginMemberId, long feedId) {

		return feedCommentMapper.searchAll(loginMemberId, feedId);
	}
}
