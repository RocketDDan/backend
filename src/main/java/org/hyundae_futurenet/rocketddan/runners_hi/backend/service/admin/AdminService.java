package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.admin;

import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;

public interface AdminService {

	List<AdminMemberResponse> selectAdminMembers();

	List<AdminFeedResponse> getAdminFeedList(Map<String, Object> params);

	int getAdminFeedTotalCount(Map<String, Object> params);
}
