package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;
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
	public List<AdminMemberResponse> getAdminMembers() {

		return adminService.selectAdminMembers();
	}
}
