package org.hyundae_futurenet.rocketddan.runners_hi.backend.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	NOT_FOUND_FEED("피드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "해당 피드가 존재하지 않습니다."),
	NOT_FOUND("존재하지 않는 데이터입니다.", HttpStatus.NOT_FOUND, ""),

	UNAUTHORIZED_ACCESS("접근 권한이 없습니다.", HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
	INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST, "요청 형식이 유효하지 않습니다.");

	private final String message;

	private final HttpStatus status;

	private final String detail;

	ErrorCode(String message, HttpStatus status, String detail) {

		this.message = message;
		this.status = status;
		this.detail = detail;
	}
}