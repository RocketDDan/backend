package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedDailyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedHourlyViewResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedViewSummaryResponse;

@Mapper
public interface AdminMapper {

	List<AdminMemberResponse> selectAdminMembers();

	List<AdminFeedResponse> findAdminFeeds(@Param("params") Map<String, Object> params);

	int countAdminFeeds(@Param("params") Map<String, Object> params);

	List<AdminMemberResponse> findAdminMembers(@Param("params") Map<String, Object> parms);

	int countAdminMembers(@Param("params") Map<String, Object> params);

	List<FeedDailyViewResponse> selectDailyViews(@Param("feedId") Long feedId,
		@Param("startDate") String startDate,
		@Param("endDate") String endDate);

	List<FeedHourlyViewResponse> selectHourlyViews(@Param("feedId") Long feedId,
		@Param("targetDate") String targetDate);

	FeedViewSummaryResponse selectFeedViewSummary(@Param("feedId") Long feedId,
		@Param("startDate") String startDate,
		@Param("endDate") String endDate);

}

