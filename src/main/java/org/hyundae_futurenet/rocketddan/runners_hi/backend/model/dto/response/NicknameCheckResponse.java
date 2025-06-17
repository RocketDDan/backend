package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "닉네임 중복 확인 응답")
@Getter
@AllArgsConstructor
public class NicknameCheckResponse {

	@Schema(description = "닉네임 중복 여부", example = "false")
	private boolean duplicate;
}
