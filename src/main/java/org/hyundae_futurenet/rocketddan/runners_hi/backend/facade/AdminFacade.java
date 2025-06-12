package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;

public interface AdminFacade {

	List<AdminMemberResponse> getAdminMembers();

	List<AdminFeedResponse> getAdminFeedList(Map<String, Object> params);

	int getAdminFeedTotalCount(Map<String, Object> params);

	List<AdminMemberResponse> getAdminMemberList(Map<String, Object> params);

	int getAdminMemberTotalCount(Map<String, Object> params);
}
