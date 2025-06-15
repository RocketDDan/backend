package org.hyundae_futurenet.rocketddan.runners_hi.backend.error;

public class CrewException extends CustomException {

	public CrewException(ErrorCode errorCode) {

		super(errorCode);
	}
}
