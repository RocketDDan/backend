package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;

@Mapper
public interface AdminMapper {

	List<AdminMemberResponse> selectAdminMembers();
}
