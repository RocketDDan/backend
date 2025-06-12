package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CommentDetailSource;

public interface FeedCommentService {

	void deleteAll(long feedId);

	void register(long loginMemberId, long feedId, String comment);

	void assertCommentExists(long loginMemberId, long feedId, long commentId);

	void update(long commentId, String newComment);

	void delete(long commentId);

	List<CommentDetailSource> searchCommentList(long loginMemberId, long feedId);
}
