package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMemberResponse {

	@Schema(description = "회원 ID", example = "123")
	private Long memberId;

	@Schema(description = "회원 닉네임", example = "러너짱")
	private String nickname;

	@Schema(description = "회원 이메일 주소", example = "runner@example.com")
	private String email;

	@Schema(description = "소속 크루 이름 (없으면 null)", example = "강남러너스")
	private String crewName;

	@Schema(
		description = "크루 내 역할",
		example = "LEADER",
		allowableValues = { "COMPANY", "ALL", "LEADER", "CREW" }
	)
	private String crewRole;
}
