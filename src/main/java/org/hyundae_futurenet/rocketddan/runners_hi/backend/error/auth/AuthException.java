package org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.CustomException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;

public class AuthException extends CustomException {

	public AuthException(final ErrorCode errorCode) {

		super(errorCode);
	}
}
