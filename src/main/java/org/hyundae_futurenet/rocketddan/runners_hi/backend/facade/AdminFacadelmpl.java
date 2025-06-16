package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedDailyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedHourlyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedViewSummaryResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MyWalletListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MyWalletResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.admin.AdminService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminFacadelmpl implements AdminFacade {

	private final AdminService adminService;

	@Override
	public List<AdminFeedResponse> getAdminFeedList(Map<String, Object> params) {

		return adminService.getAdminFeedList(params);
	}

	@Override
	public int getAdminFeedTotalCount(Map<String, Object> params) {

		return adminService.getAdminFeedTotalCount(params);
	}

	@Override
	public List<AdminMemberResponse> getAdminMemberList(Map<String, Object> params) {

		return adminService.getAdminMemberList(params);
	}

	@Override
	public int getAdminMemberTotalCount(Map<String, Object> params) {

		return adminService.getAdminMemberTotalCount(params);
	}

	@Override
	public List<FeedDailyViewResponse> getDailyViews(Long feedId, String startDate, String endDate) {

		return adminService.getDailyViews(feedId, startDate, endDate);
	}

	@Override
	public List<FeedHourlyViewResponse> getHourlyView(Long feedId, String targetDate) {

		return adminService.getHourlyViews(feedId, targetDate);
	}

	@Override
	public FeedViewSummaryResponse getViewSummary(Long feedId, String startDate, String endDate) {

		return adminService.getViewSummary(feedId, startDate, endDate);
	}

	@Override
	public MyWalletListResponse getMyWalletList(Long memberId, int page, int perPage) {

		Map<String, Object> params = new HashMap<>();
		params.put("memberId", memberId);
		params.put("offset", (page - 1) * perPage);
		params.put("perPage", perPage);

		log.info("params={}", params);

		List<MyWalletResponse> list = adminService.getMyWalletList(params);
		log.info("params in countMyWalletList: {}", params);

		int totalCount = adminService.getMyWalletTotalCount(params);

		return new MyWalletListResponse(list, totalCount);
	}
}
