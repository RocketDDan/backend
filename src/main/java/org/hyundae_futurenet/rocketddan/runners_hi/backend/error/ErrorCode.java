package org.hyundae_futurenet.rocketddan.runners_hi.backend.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	NOT_FOUND_FEED("피드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "해당 피드가 존재하지 않습니다."),
	NOT_FOUND("존재하지 않는 데이터입니다.", HttpStatus.NOT_FOUND, ""),

	UNAUTHORIZED_ACCESS("접근 권한이 없습니다.", HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
	INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST, "요청 형식이 유효하지 않습니다."),

	// MEMBER
	MEMBER_INVALID_EMAIL_AND_PASSWORD("아이디나 비밀번호가 다릅니다.", HttpStatus.BAD_REQUEST, "아이디나 비밀번호가 다릅니다."),
	SIGNUP_MEMBER_NICKNAME_DUPLICATED("이미 사용중인 닉네임입니다.", HttpStatus.BAD_REQUEST, "이미 사용중인 닉네임입니다."),
	MEMBER_DELETED("탈퇴한 회원입니다.", HttpStatus.BAD_REQUEST, "탈퇴한 회원입니다. 다시 가입해주세요."),
	MEMBER_NOT_EXIST("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST, "해당 회원이 존재하지 않습니다.");

	private final String message;

	private final HttpStatus status;

	private final String detail;

	ErrorCode(String message, HttpStatus status, String detail) {

		this.message = message;
		this.status = status;
		this.detail = detail;
	}
}