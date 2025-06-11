package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.admin;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.admin.AdminMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServicelmpl implements AdminService {

	private final AdminMapper adminMapper;

	@Override
	public List<AdminMemberResponse> selectAdminMembers() {

		return adminMapper.selectAdminMembers();
	}
}
