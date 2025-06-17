package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.AdminOnly;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminFacade adminFacade;

	// 회원 목록 조회
	@GetMapping("/members")
	@AdminOnly
	public ResponseEntity<AdminMemberListResponse> getAdminMembers(
		@Auth final Accessor accessor,
		@RequestParam(required = false) String keyword,
		@RequestParam(required = false, defaultValue = "1") int page,
		@RequestParam(required = false, defaultValue = "6") int perPage
	) {

		Map<String, Object> params = new HashMap<>();
		if (keyword != null && !keyword.isEmpty()) {
			params.put("keyword", keyword);
		}
		params.put("offset", (page - 1) * perPage);
		params.put("perPage", perPage);

		List<AdminMemberResponse> members = adminFacade.getAdminMemberList(params);
		int totalCount = adminFacade.getAdminMemberTotalCount(params);

		return ResponseEntity.ok(new AdminMemberListResponse(members, totalCount));
	}

	// 리워드 홍보 피드 조회
	@GetMapping("/rewards")
	@AdminOnly
	public ResponseEntity<AdminFeedListResponse> getAdminFeeds(
		@Auth final Accessor accessor,
		@RequestParam(required = false) String keyword,
		@RequestParam(required = false, defaultValue = "1") int page,
		@RequestParam(required = false, defaultValue = "6") int perPage,
		@RequestParam(required = false) String month,
		@RequestParam(required = false) String day
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

	@GetMapping("/feeds/{id}/views/daily")
	@CompanyOnly
	public ResponseEntity<List<FeedDailyViewResponse>> getDailyViews(
		@Auth final Accessor accessor,
		@PathVariable("id") Long feedId,
		@RequestParam String startDate,
		@RequestParam String endDate) {

		List<FeedDailyViewResponse> result = adminFacade.getDailyViews(feedId, startDate, endDate);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/feeds/{id}/views/hourly")
	@CompanyOnly
	public ResponseEntity<List<FeedHourlyViewResponse>> getHourlyViews(
		@Auth final Accessor accessor,
		@PathVariable("id") Long feedId,
		@RequestParam String targetDate) {

		List<FeedHourlyViewResponse> result = adminFacade.getHourlyView(feedId, targetDate);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/feeds/{id}/views/summary")
	@CompanyOnly
	public ResponseEntity<FeedViewSummaryResponse> getViewSummary(
		@Auth final Accessor accessor,
		@PathVariable("id") Long feedId,
		@RequestParam String startDate,
		@RequestParam String endDate) {

		FeedViewSummaryResponse result = adminFacade.getViewSummary(feedId, startDate, endDate);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/my-wallet")
	@CompanyOnly
	public ResponseEntity<MyWalletListResponse> getMyWallet(
		@Auth final Accessor accessor,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "6") int perPage
	) {

		return ResponseEntity.ok(adminFacade.getMyWalletList(accessor.getMemberId(), page, perPage));
	}

}
