package org.hyundae_futurenet.rocketddan.runners_hi.backend.error;

public class FeedException extends CustomException {

	public FeedException(ErrorCode errorCode) {

		super(errorCode);
	}
}