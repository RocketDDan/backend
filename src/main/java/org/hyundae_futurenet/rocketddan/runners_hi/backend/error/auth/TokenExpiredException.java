package org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenExpiredException extends RuntimeException {

	public TokenExpiredException() {

		super("만료된 토큰입니다. 재발행해주세요.");
	}
}
