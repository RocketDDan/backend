package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.admin;

import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedDailyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedHourlyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedViewSummaryResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MyWalletResponse;

/**
 * 관리자(Admin) 또는 회사 계정 전용 기능을 처리하는 서비스 인터페이스입니다.
 * 피드 관리, 회원 조회, 조회수 통계, 수익 정산 등 다양한 기능을 제공합니다.
 */
public interface AdminService {

	/**
	 * 관리자 권한으로 피드 목록을 조회합니다.
	 *
	 * @param params 검색 및 페이징 파라미터
	 * @return 관리자 피드 목록 응답 리스트
	 */
	List<AdminFeedResponse> getAdminFeedList(Map<String, Object> params);

	/**
	 * 피드 목록의 전체 개수를 조회합니다.
	 *
	 * @param params 검색 조건 파라미터
	 * @return 전체 피드 수
	 */
	int getAdminFeedTotalCount(Map<String, Object> params);

	/**
	 * 관리자 권한으로 회원 목록을 조회합니다.
	 *
	 * @param params 검색 및 필터 파라미터
	 * @return 관리자 회원 목록 응답 리스트
	 */
	List<AdminMemberResponse> getAdminMemberList(Map<String, Object> params);

	/**
	 * 회원 목록의 전체 개수를 조회합니다.
	 *
	 * @param params 검색 조건 파라미터
	 * @return 전체 회원 수
	 */
	int getAdminMemberTotalCount(Map<String, Object> params);

	/**
	 * 특정 피드의 일별 조회수를 조회합니다.
	 *
	 * @param feedId 피드 ID
	 * @param startDate 시작 날짜 (yyyy-MM-dd)
	 * @param endDate 종료 날짜 (yyyy-MM-dd)
	 * @return 일별 조회수 응답 리스트
	 */
	List<FeedDailyViewResponse> getDailyViews(Long feedId, String startDate, String endDate);

	/**
	 * 특정 피드의 특정 날짜에 대한 시간대별 조회수를 조회합니다.
	 *
	 * @param feedId 피드 ID
	 * @param targetDate 조회할 날짜 (yyyy-MM-dd)
	 * @return 시간대별 조회수 응답 리스트
	 */
	List<FeedHourlyViewResponse> getHourlyViews(Long feedId, String targetDate);

	/**
	 * 특정 피드에 대한 조회 통계를 요약 형태로 조회합니다.
	 *
	 * @param feedId 피드 ID
	 * @param startDate 시작 날짜
	 * @param endDate 종료 날짜
	 * @return 조회수, 순방문자 등 요약 통계 응답
	 */
	FeedViewSummaryResponse getViewSummary(Long feedId, String startDate, String endDate);

	/**
	 * 회사 계정이 업로드한 피드에 대한 수익 내역(지갑 정보)을 조회합니다.
	 *
	 * @param params 검색 및 페이징 파라미터 (memberId, 기간 등 포함 가능)
	 * @return 수익 내역 리스트
	 */
	List<MyWalletResponse> getMyWalletList(Map<String, Object> params);

	/**
	 * 수익 내역 전체 건수를 조회합니다.
	 *
	 * @param params 검색 조건 파라미터
	 * @return 전체 건수
	 */
	int getMyWalletTotalCount(Map<String, Object> params);
}