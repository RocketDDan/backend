package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

public interface FeedCommentService {

	void deleteAll(long feedId);

	void register(long loginMemberId, long feedId, String comment);

	void assertCommentExists(long loginMemberId, long feedId, String commentId);

	void update(String commentId, String newComment);
}
