package org.hyundae_futurenet.rocketddan.runners_hi.backend.error.exception;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.CustomException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;

public class BadRequestException extends CustomException {

	public BadRequestException() {

		super(ErrorCode.INVALID_REQUEST);
	}
}
