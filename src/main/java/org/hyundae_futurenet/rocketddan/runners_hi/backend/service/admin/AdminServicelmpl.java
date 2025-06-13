package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.admin;

import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminFeedResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.admin.AdminMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServicelmpl implements AdminService {

	private final AdminMapper adminMapper;

	@Override
	public List<AdminFeedResponse> getAdminFeedList(Map<String, Object> params) {

		return adminMapper.findAdminFeeds(params);
	}

	@Override
	public int getAdminFeedTotalCount(Map<String, Object> params) {

		return adminMapper.countAdminFeeds(params);
	}

	@Override
	public List<AdminMemberResponse> getAdminMemberList(Map<String, Object> params) {

		return adminMapper.findAdminMembers(params);
	}

	@Override
	public int getAdminMemberTotalCount(Map<String, Object> params) {

		return adminMapper.countAdminMembers(params);
	}
}
