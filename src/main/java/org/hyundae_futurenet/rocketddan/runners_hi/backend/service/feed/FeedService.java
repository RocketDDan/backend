package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.FeedListSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;

/**
 * 피드(Feed) 관련 핵심 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 * 일반 피드와 광고 피드(유료) 저장, 수정, 삭제 및 검색 기능을 제공합니다.
 */
public interface FeedService {

	/**
	 * 필터 조건을 기반으로 피드 목록을 조회합니다.
	 *
	 * @param loginMemberId 로그인한 사용자 ID
	 * @param feedSearchFilter 검색 필터 (지역, 정렬 기준 등)
	 * @return 피드 리스트 (비즈니스용 내부 응답 객체)
	 */
	List<FeedListSource> searchFeedsByFilter(long loginMemberId, FeedSearchFilter feedSearchFilter);

	/**
	 * 일반 사용자의 피드를 저장합니다.
	 *
	 * @param loginMemberId 작성자 ID
	 * @param content 피드 내용
	 * @param lat 위도 (nullable)
	 * @param lng 경도 (nullable)
	 * @return 생성된 피드 ID
	 */
	long save(long loginMemberId, String content, Double lat, Double lng);

	/**
	 * 광고 피드를 '대기' 상태로 저장합니다. (결제 승인 전)
	 *
	 * @param loginMemberId 기업 회원 ID
	 * @param content 피드 내용
	 * @param lat 위도
	 * @param lng 경도
	 * @return 생성된 피드 ID
	 */
	long saveAdvertiseFeedWithStatusWait(long loginMemberId, String content, Double lat, Double lng);

	/**
	 * 피드를 삭제합니다.
	 *
	 * @param feedId 삭제할 피드 ID
	 */
	void delete(long feedId);

	/**
	 * 피드가 존재하는지, 그리고 요청자에게 접근 권한이 있는지 검증합니다.
	 * 피드 수정/삭제 시 권한 확인용으로 사용됩니다.
	 *
	 * @param loginMemberId 요청자 ID
	 * @param feedId 피드 ID
	 * @throws 예외 발생 시 권한 없음 또는 존재하지 않음
	 */
	void assertFeedExists(long loginMemberId, long feedId);

	/**
	 * 피드 내용을 수정합니다.
	 *
	 * @param feedId 수정할 피드 ID
	 * @param newContent 새로운 내용
	 * @param newLat 새로운 위도
	 * @param newLng 새로운 경도
	 */
	void update(long feedId, String newContent, Double newLat, Double newLng);

	/**
	 * 광고 피드의 상태를 '승인'으로 변경합니다.
	 * 결제 완료 후 호출됩니다.
	 *
	 * @param feedId 대상 피드 ID
	 */
	void updateAdvertiseFeedStatusWithApproved(long feedId);
}