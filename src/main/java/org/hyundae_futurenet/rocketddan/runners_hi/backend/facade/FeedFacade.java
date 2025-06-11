package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.CommentDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedListResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FeedFacade {

	List<FeedListResponse> searchFeedsByFilter(long loginMemberId, FeedSearchFilter feedSearchFilter);

	void uploadFeed(long loginMemberId, String content, Double lat, Double lng, List<MultipartFile> fileList);

	void deleteFeed(long loginMemberId, long feedId);

	void likeFeed(long loginMemberId, long feedId);

	void unlikeFeed(long loginMemberId, long feedId);

	void registerComment(long loginMemberId, long feedId, String comment);

	void updateComment(long loginMemberId, long feedId, String commentId, String newComment);

	void deleteCommit(long loginMemberId, long feedId, String commentId);

	List<CommentDetailResponse> searchCommentList(long loginMemberId, long feedId);

	void updateFeed(long loginMemberId, long feedId, String newContent, Double newLat, Double newLng,
		List<MultipartFile> newfileList);
}
