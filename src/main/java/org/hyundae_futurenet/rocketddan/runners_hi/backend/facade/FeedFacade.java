package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.KakaoPayApproveRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.feed.CommentDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.feed.FeedListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.external_dto.response.KakaoPayReadyResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FeedFacade {

	List<FeedListResponse> searchFeedsByFilter(
		long loginMemberId,
		FeedSearchFilter feedSearchFilter);

	void uploadFeed(
		long loginMemberId,
		String content,
		Double lat, Double lng,
		List<MultipartFile> fileList);

	KakaoPayReadyResponse uploadFeedByCompany(
		long loginMemberId,
		String content,
		Double lat, Double lng,
		List<MultipartFile> fileList,
		int payAmount);

	void deleteFeed(long loginMemberId, long feedId);

	void updateFeed(
		long loginMemberId,
		long feedId,
		String newContent,
		Double newLat, Double newLng,
		List<MultipartFile> newfileList);

	void likeFeed(long loginMemberId, long feedId);

	void unlikeFeed(long loginMemberId, long feedId);

	void registerComment(long loginMemberId, long feedId, String comment);

	void updateComment(long loginMemberId, long feedId, long commentId, String newComment);

	void deleteCommit(long loginMemberId, long feedId, long commentId);

	List<CommentDetailResponse> searchCommentList(long loginMemberId, long feedId);

	void approveFeedPay(KakaoPayApproveRequest kakaoPayApproveRequest);
}
