package org.hyundae_futurenet.rocketddan.runners_hi.backend.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	NOT_FOUND_FEED("피드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "해당 피드가 존재하지 않습니다."),
	NOT_FOUND("존재하지 않는 데이터입니다.", HttpStatus.NOT_FOUND, ""),

	UNAUTHORIZED_ACCESS("접근 권한이 없습니다.", HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
	INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST, "요청 형식이 유효하지 않습니다."),

	/** 크루 */
	NOT_FOUND_CREW("크루를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "해당 크루가 존재하지 않습니다."),
	NOT_FOUND_CREW_JOIN_REQUEST("크루 가입 요청을 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "해당 가입 요청이 존재하지 않습니다."),
	DUPLICATED_CREW_NAME("중복된 크루명입니다.", HttpStatus.BAD_REQUEST, "이미 존재하는 크루명입니다."),
	ALREADY_EXIST_CREW_MEMBER("이미 크루 멤버입니다.", HttpStatus.BAD_REQUEST, "더 이상 크루에 가입할 수 없습니다."),
	ALREADY_EXIST_CREW_JOIN_REQUEST("이미 크루 가입 요청 이력이 있습니다.", HttpStatus.BAD_REQUEST, "더 이상 가입 요청을 할 수 없습니다.");

	private final String message;

	private final HttpStatus status;

	private final String detail;

	ErrorCode(String message, HttpStatus status, String detail) {

		this.message = message;
		this.status = status;
		this.detail = detail;
	}
}