package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.admin;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;

public interface AdminService {

	List<AdminMemberResponse> selectAdminMembers();
}
