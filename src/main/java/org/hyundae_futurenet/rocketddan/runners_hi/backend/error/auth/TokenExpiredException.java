package org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.CustomException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;

public class TokenExpiredException extends CustomException {

	public TokenExpiredException() {

		super(ErrorCode.TOKEN_EXPIRED);
	}
}
