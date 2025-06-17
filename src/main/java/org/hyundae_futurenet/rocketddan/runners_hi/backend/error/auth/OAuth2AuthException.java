package org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;

public class OAuth2AuthException extends AuthException {

	public OAuth2AuthException(final ErrorCode errorCode) {

		super(errorCode);
	}
}
