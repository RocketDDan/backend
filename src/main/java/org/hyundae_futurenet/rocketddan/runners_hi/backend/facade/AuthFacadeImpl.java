package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant.Role;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.auth.JwtTokenProvider;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthFacadeImpl implements AuthFacade {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public String reissueAccessToken(String refreshToken) {

		jwtTokenProvider.validateToken(refreshToken);

		long memberId = jwtTokenProvider.getMemberId(refreshToken);
		Role role = jwtTokenProvider.getRole(refreshToken);
		String memberEmail = jwtTokenProvider.getMemberEmail(refreshToken);
		return jwtTokenProvider.generateAccessToken(memberId, memberEmail, role);
	}
}
