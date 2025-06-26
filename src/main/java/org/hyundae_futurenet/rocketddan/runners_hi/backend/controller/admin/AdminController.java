package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.AdminOnly;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.CompanyAdminOnly;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.CompanyOnly;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.AdminFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedDailyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedHourlyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedViewSummaryResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MyWalletListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "관리자 및 회사 API", description = "관리자 및 회사 전용 API")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminFacade adminFacade;

	@Operation(summary = "회원 목록 조회", description = "관리자 권한으로 회원 목록을 페이징 조회합니다.")
	@GetMapping("/members")
	@AdminOnly
	public ResponseEntity<AdminMemberListResponse> getAdminMembers(
		// @Auth final Accessor accessor,
		@Parameter(description = "검색 키워드 (닉네임, 이메일, 크루 이름)") @RequestParam(required = false) String keyword,
		@Parameter(description = "페이지 번호") @RequestParam(required = false, defaultValue = "1") int page,
		@Parameter(description = "페이지 당 항목 수") @RequestParam(required = false, defaultValue = "6") int perPage,
		@Parameter(description = "역할 필터 (COMPANY 등)") @RequestParam(required = false) String role

	) {

		log.info("role={}", role);
		Map<String, Object> params = new HashMap<>();
		if (keyword != null && !keyword.isEmpty()) {
			params.put("keyword", keyword);
		}
		if (role != null && !role.isEmpty()) {
			params.put("role", role);
		}
		params.put("offset", (page - 1) * perPage);
		params.put("perPage", perPage);

		List<AdminMemberResponse> members = adminFacade.getAdminMemberList(params);
		int totalCount = adminFacade.getAdminMemberTotalCount(params);

		return ResponseEntity.ok(new AdminMemberListResponse(members, totalCount));
	}

	@Operation(summary = "리워드 피드 목록 조회", description = "리워드 피드 목록을 필터링하여 페이징 조회합니다.")
	@GetMapping("/rewards")
	@AdminOnly
	public ResponseEntity<AdminFeedListResponse> getAdminFeeds(
		// @Auth final Accessor accessor,
		@Parameter(description = "검색 키워드") @RequestParam(required = false) String keyword,
		@Parameter(description = "페이지 번호") @RequestParam(required = false, defaultValue = "1") int page,
		@Parameter(description = "페이지 당 항목 수") @RequestParam(required = false, defaultValue = "6") int perPage,
		@Parameter(description = "월 필터 (예: 06)") @RequestParam(required = false) String month,
		@Parameter(description = "일 필터 (예: 17)") @RequestParam(required = false) String day
	) {

		Map<String, Object> params = new HashMap<>();

		if (keyword != null && !keyword.isEmpty()) {
			params.put("keyword", keyword);
		}
		if (month != null && !month.isEmpty()) {
			params.put("month", month);
		}
		if (day != null && !day.isEmpty()) {
			params.put("day", day);
		}

		params.put("offset", (page - 1) * perPage);
		params.put("perPage", perPage);

		List<AdminFeedResponse> feeds = adminFacade.getAdminFeedList(params);
		int totalCount = adminFacade.getAdminFeedTotalCount(params);

		return ResponseEntity.ok(new AdminFeedListResponse(feeds, totalCount));
	}

	@Operation(summary = "일별 피드 클릭 수 조회", description = "특정 피드의 날짜별 클릭 수를 조회합니다.")
	@GetMapping("/feeds/{id}/views/daily")
	@CompanyAdminOnly
	public ResponseEntity<List<FeedDailyViewResponse>> getDailyViews(
		// @Auth final Accessor accessor,
		@Parameter(description = "피드 ID") @PathVariable("id") Long feedId,
		@Parameter(description = "시작일 (YYYY-MM-DD)") @RequestParam String startDate,
		@Parameter(description = "종료일 (YYYY-MM-DD)") @RequestParam String endDate) {

		List<FeedDailyViewResponse> result = adminFacade.getDailyViews(feedId, startDate, endDate);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "시간대별 클릭 수 조회", description = "특정 피드의 특정 날짜에 대한 시간대별 클릭 수를 조회합니다.")
	@GetMapping("/feeds/{id}/views/hourly")
	@CompanyAdminOnly
	public ResponseEntity<List<FeedHourlyViewResponse>> getHourlyViews(
		// @Auth final Accessor accessor,
		@Parameter(description = "피드 ID") @PathVariable("id") Long feedId,
		@Parameter(description = "조회 일자 (YYYY-MM-DD)") @RequestParam String targetDate) {

		List<FeedHourlyViewResponse> result = adminFacade.getHourlyView(feedId, targetDate);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "피드 조회 요약 통계", description = "특정 날짜에 피드의 클릭 수, 순 방문자 수 등의 요약 통계를 조회합니다.")
	@GetMapping("/feeds/{id}/views/summary")
	@CompanyAdminOnly
	public ResponseEntity<FeedViewSummaryResponse> getViewSummary(
		// @Auth final Accessor accessor,
		@Parameter(description = "피드 ID") @PathVariable("id") Long feedId,
		@Parameter(description = "시작일 (YYYY-MM-DD)") @RequestParam String startDate,
		@Parameter(description = "종료일 (YYYY-MM-DD)") @RequestParam String endDate) {

		FeedViewSummaryResponse result = adminFacade.getViewSummary(feedId, startDate, endDate);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "내 지갑 정보 조회", description = "회사 계정이 등록한 피드의 수익 정보를 조회합니다.")
	@GetMapping("/my-wallet")
	@CompanyOnly
	public ResponseEntity<MyWalletListResponse> getMyWallet(
		@Auth final Accessor accessor,
		@Parameter(description = "페이지 번호") @RequestParam(defaultValue = "1") int page,
		@Parameter(description = "페이지 당 항목수") @RequestParam(defaultValue = "6") int perPage
	) {

		return ResponseEntity.ok(adminFacade.getMyWalletList(accessor.getMemberId(), page, perPage));
	}

}
