package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant.Role;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

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
public class SignUpRequest {

	@Schema(description = "이메일", example = "runners-hi@naver.com")
	@NotBlank(message = "이메일이 입력되지 않았습니다.")
	@Email(message = "유효한 이메일을 입력하세요.")
	private String email;

	@Schema(description = "비밀번호. 영어와 숫자를 반드시 포함해야 합니다.", example = "password1234")
	@NotBlank(message = "비밀번호가 입력되지 않았습니다.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).*$", message = "영어와 숫자를 반드시 포함해야합니다.")
	@Size(min = 8, max = 16, message = "비밀번호는 8~16자 사이로 입력해주세요.")
	private String password;

	@Schema(description = "닉네임. 한글, 영어, 숫자만 입력 가능합니다.", example = "러너스하잉")
	@NotBlank(message = "닉네임이 입력되지 않았습니다.")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임은 한글, 영어, 숫자만 입력 가능합니다.")
	@Size(min = 2, max = 20, message = "닉네임은 2~20자 사이로 입력해주세요.")
	private String nickname;

	@Schema(description = "전화번호. '-' 없이 10자리 또는 11자리 숫자만 입력 가능합니다.", example = "01012345678")
	@NotBlank(message = "전화번호가 입력되지 않았습니다.")
	@Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 '-' 없이 10자리 또는 11자리 숫자만 입력 가능합니다.")
	private String phone;

	@Schema(description = "기업회원 여부", defaultValue = "false")
	private boolean companyMember;

	public Member toMember(PasswordEncoder passwordEncoder) {

		return new Member(
			email,
			nickname,
			phone,
			passwordEncoder.encode(password),
			companyMember ? Role.COMPANY : Role.USER
		);
	}
}