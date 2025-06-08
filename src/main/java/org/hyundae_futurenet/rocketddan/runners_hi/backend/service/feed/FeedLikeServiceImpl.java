package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed.FeedLikeMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedLikeServiceImpl implements FeedLikeService {

	private final FeedLikeMapper feedLikeMapper;

	@Override
	public void like(long loginMemberId, long feedId) {

		feedLikeMapper.insert(loginMemberId, feedId);
	}

	@Override
	public void unlike(long loginMemberId, long feedId) {

		feedLikeMapper.delete(loginMemberId, feedId);
	}
}
