package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "크루 가입 요청 DTO")
public record CrewJoinRequest(
	@Schema(description = "크루 가입 요청 메세지", example = "열심히 활동하겠습니다! 받아주세여!")
	@Size(max = 1000, message = "가입 요청 메세지는 최대 1000자 입니다.")
	String requestMessage
) {

}
