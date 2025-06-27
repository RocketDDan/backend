package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedDailyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedHourlyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedViewSummaryResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MyWalletListResponse;

/**
 * 관리자 페이지 및 회사 전용 API를 위한 비즈니스 로직 조합 클래스
 * 각 도메인의 서비스 로직을 조합해 제공하는 퍼사드 인터페이스입니다.
 */
public interface AdminFacade {

	/**
	 * 관리자용 피드 목록을 조회합니다.
	 *
	 * @param params 검색 및 페이징 파라미터 (keyword, offset, perPage 등)
	 * @return 관리자 피드 응답 리스트
	 */
	List<AdminFeedResponse> getAdminFeedList(Map<String, Object> params);

	/**
	 * 관리자 피드 목록의 전체 개수를 조회합니다.
	 *
	 * @param params 검색 조건 파라미터
	 * @return 전체 피드 수
	 */
	int getAdminFeedTotalCount(Map<String, Object> params);

	/**
	 * 관리자용 회원 목록을 조회합니다.
	 *
	 * @param params 검색 및 페이징 파라미터 (keyword, role, offset, perPage 등)
	 * @return 관리자 회원 응답 리스트
	 */
	List<AdminMemberResponse> getAdminMemberList(Map<String, Object> params);

	/**
	 * 관리자 회원 목록의 전체 개수를 조회합니다.
	 *
	 * @param params 검색 조건 파라미터
	 * @return 전체 회원 수
	 */
	int getAdminMemberTotalCount(Map<String, Object> params);

	/**
	 * 피드별 일자 단위 클릭 수를 조회합니다.
	 *
	 * @param feedId    피드 ID
	 * @param startDate 시작일 (yyyy-MM-dd)
	 * @param endDate   종료일 (yyyy-MM-dd)
	 * @return 일별 클릭 수 응답 리스트
	 */
	List<FeedDailyViewResponse> getDailyViews(Long feedId, String startDate, String endDate);

	/**
	 * 피드의 특정 날짜에 대한 시간대별 클릭 수를 조회합니다.
	 *
	 * @param feedId     피드 ID
	 * @param targetDate 조회할 날짜 (yyyy-MM-dd)
	 * @return 시간대별 클릭 수 응답 리스트
	 */
	List<FeedHourlyViewResponse> getHourlyView(Long feedId, String targetDate);

	/**
	 * 피드의 클릭 수 요약 통계를 조회합니다.
	 *
	 * @param feedId    피드 ID
	 * @param startDate 시작일 (yyyy-MM-dd)
	 * @param endDate   종료일 (yyyy-MM-dd)
	 * @return 클릭 수, 방문자 수 등의 요약 통계 응답
	 */
	FeedViewSummaryResponse getViewSummary(Long feedId, String startDate, String endDate);

	/**
	 * 회사 계정의 수익 내역(지갑 정보)을 조회합니다.
	 *
	 * @param memberId 회사 회원 ID
	 * @param page     페이지 번호
	 * @param perPage  페이지 당 항목 수
	 * @return 지갑 내역 응답 객체
	 */
	MyWalletListResponse getMyWalletList(Long memberId, int page, int perPage);
}