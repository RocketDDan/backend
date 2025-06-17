package org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;

public class InvalidAuthorityException extends AuthException {

	public InvalidAuthorityException() {

		super(ErrorCode.INVALID_AUTHORITY);
	}
}
