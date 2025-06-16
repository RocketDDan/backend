package org.hyundae_futurenet.rocketddan.runners_hi.backend.error.exception;

public class BadRequestException extends RuntimeException {

	public BadRequestException() {

		super("올바르지 않는 요청입니다.");
	}
}
