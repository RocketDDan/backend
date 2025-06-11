package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;

public interface AdminFacade {

	List<AdminMemberResponse> getAdminMembers();
}
