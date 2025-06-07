package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

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
}
