package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

/**
 * 피드 좋아요(Like) 기능을 처리하는 서비스 인터페이스입니다.
 * 좋아요 등록 및 취소 기능을 제공합니다.
 */
public interface FeedLikeService {

	/**
	 * 피드에 좋아요를 등록합니다.
	 *
	 * @param loginMemberId 요청한 사용자 ID
	 * @param feedId 좋아요를 누를 피드 ID
	 */
	void like(long loginMemberId, long feedId);

	/**
	 * 피드에 등록된 좋아요를 취소합니다.
	 *
	 * @param loginMemberId 요청한 사용자 ID
	 * @param feedId 좋아요를 취소할 피드 ID
	 */
	void unlike(long loginMemberId, long feedId);
}