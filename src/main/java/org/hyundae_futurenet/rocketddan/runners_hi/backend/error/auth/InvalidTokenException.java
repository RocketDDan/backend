package org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;

public class InvalidTokenException extends AuthException {

	public InvalidTokenException() {

		super(ErrorCode.INVALID_TOKEN);
	}
}
