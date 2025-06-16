package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.member.MemberException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant.Role;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.LoginRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.member.MemberService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.auth.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthFacadeImpl implements AuthFacade {

	private final JwtTokenProvider jwtTokenProvider;

	private final PasswordEncoder passwordEncoder;

	private final MemberService memberService;

	@Override
	public String reissueAccessToken(String refreshToken) {

		jwtTokenProvider.validateToken(refreshToken);

		long memberId = jwtTokenProvider.getMemberId(refreshToken);
		Role role = jwtTokenProvider.getRole(refreshToken);
		String memberEmail = jwtTokenProvider.getMemberEmail(refreshToken);
		return jwtTokenProvider.generateAccessToken(memberId, memberEmail, role);
	}

	@Override
	public Member login(LoginRequest loginRequest) {

		String email = loginRequest.getEmail();
		String requestPassword = loginRequest.getPassword();
		String realPassword = memberService.findPasswordByEmail(email)
			.orElseThrow(() -> new MemberException(ErrorCode.MEMBER_INVALID_EMAIL_AND_PASSWORD));
		if (!passwordEncoder.matches(requestPassword, realPassword)) {
			throw new MemberException(ErrorCode.MEMBER_INVALID_EMAIL_AND_PASSWORD);
		}

		return memberService.findByEmail(email)
			.orElseThrow(() -> new MemberException(ErrorCode.MEMBER_INVALID_EMAIL_AND_PASSWORD));
	}
}
