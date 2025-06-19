package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

	@Schema(description = "이메일", example = "runners-hi@naver.com")
	@NotBlank(message = "이메일이 입력되지 않았습니다.")
	@Email(message = "유효한 이메일을 입력하세요.")
	private String email;

	@Schema(description = "비밀번호. 영문 대소문자, 숫자, 특수문자(@!#?) 포함 8~16자로 입력.", example = "Password123!")
	@NotBlank(message = "비밀번호가 입력되지 않았습니다.")
	@Pattern(
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#?])[a-zA-Z\\d@!#?]{8,16}$",
		message = "영문 대소문자, 숫자, 특수문자(@!#?) 포함 8~16자로 입력"
	)
	@Size(min = 8, max = 16, message = "비밀번호는 8~16자 사이로 입력해주세요.")
	private String password;
}
