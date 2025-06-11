package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;

@Mapper
public interface AdminMapper {

	List<AdminMemberResponse> selectAdminMembers();

	List<AdminFeedResponse> findAdminFeeds(@Param("params") Map<String, Object> params);

	int countAdminFeeds(@Param("params") Map<String, Object> params);
}
