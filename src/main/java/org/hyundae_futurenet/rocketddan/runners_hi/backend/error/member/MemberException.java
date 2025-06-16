package org.hyundae_futurenet.rocketddan.runners_hi.backend.error.member;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.CustomException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;

public class MemberException extends CustomException {

	public MemberException(ErrorCode errorCode) {

		super(errorCode);
	}
}
