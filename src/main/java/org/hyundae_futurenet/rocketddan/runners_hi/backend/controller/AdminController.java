package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.AdminFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminFacade adminFacade;

	@GetMapping("/members")
	public ResponseEntity<AdminMemberListResponse> getAdminMembers(
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

	@GetMapping("/rewards")
	public ResponseEntity<AdminFeedListResponse> getAdminFeeds(
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

		List<AdminFeedResponse> feeds = adminFacade.getAdminFeedList(params);
		int totalCount = adminFacade.getAdminFeedTotalCount(params);

		return ResponseEntity.ok(new AdminFeedListResponse(feeds, totalCount));
	}

}
