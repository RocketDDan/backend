package org.hyundae_futurenet.rocketddan.runners_hi.backend.exception.auth;

public class OAuth2AuthException extends AuthException {
	
	public OAuth2AuthException(final String message) {

		super(message);
	}

	public OAuth2AuthException() {

		this("OAuth2 인증에 실패했습니다.");
	}
}
