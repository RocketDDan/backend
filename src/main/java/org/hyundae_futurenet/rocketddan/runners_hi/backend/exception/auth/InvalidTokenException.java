package org.hyundae_futurenet.rocketddan.runners_hi.backend.exception.auth;

public class InvalidTokenException extends AuthException {

	public InvalidTokenException(final String message) {

		super(message);
	}

	public InvalidTokenException() {

		this("유효하지 않은 토큰입니다.");
	}
}
