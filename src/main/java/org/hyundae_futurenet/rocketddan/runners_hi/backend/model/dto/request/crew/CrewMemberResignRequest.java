package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew;

import jakarta.validation.constraints.NotBlank;

public record CrewMemberResignRequest(
	@NotBlank(message = "비밀번호를 입력해주세요.")
	String password
) {

}
