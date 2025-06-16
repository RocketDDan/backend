package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.admin;

import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedDailyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedHourlyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedViewSummaryResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MyWalletResponse;

public interface AdminService {

	List<AdminFeedResponse> getAdminFeedList(Map<String, Object> params);

	int getAdminFeedTotalCount(Map<String, Object> params);

	List<AdminMemberResponse> getAdminMemberList(Map<String, Object> params);

	int getAdminMemberTotalCount(Map<String, Object> params);

	List<FeedDailyViewResponse> getDailyViews(Long feedId, String startDate, String endDate);

	List<FeedHourlyViewResponse> getHourlyViews(Long feedId, String targetDate);

	FeedViewSummaryResponse getViewSummary(Long feedId, String startDate, String endDate);

	List<MyWalletResponse> getMyWalletList(Map<String, Object> params);

	int getMyWalletTotalCount(Map<String, Object> params);
}
