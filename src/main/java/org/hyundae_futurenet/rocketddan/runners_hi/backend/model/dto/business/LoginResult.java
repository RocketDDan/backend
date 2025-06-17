package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResult {

	private String accessTokenCookie;

	private String refreshTokenCookie;

	private String redirectUrl;
}
