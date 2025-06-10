package org.hyundae_futurenet.rocketddan.runners_hi.backend.exception.member;

public class MemberWithdrawnException extends MemberException {

	public MemberWithdrawnException() {

		super("탈퇴한 회원입니다. 다시 가입해주세요.");
	}
}
