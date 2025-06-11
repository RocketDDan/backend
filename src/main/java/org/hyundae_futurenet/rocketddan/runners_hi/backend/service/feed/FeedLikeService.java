package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

public interface FeedLikeService {

	void like(long loginMemberId, long feedId);

	void unlike(long loginMemberId, long feedId);
}
