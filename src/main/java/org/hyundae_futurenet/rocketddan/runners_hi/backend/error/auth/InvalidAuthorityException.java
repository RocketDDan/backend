package org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth;

public class InvalidAuthorityException extends AuthException {

	public InvalidAuthorityException() {

		super("해당 요청에 대한 접근 권한이 없습니다.");
	}
}
