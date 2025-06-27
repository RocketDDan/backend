package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.LoginRequest;

/**
 * 인증 및 로그인 관련 주요 비즈니스 로직을 담당하는 퍼사드 인터페이스입니다.
 */
public interface AuthFacade {

	/**
	 * 리프레시 토큰을 이용해 새로운 액세스 토큰을 재발급합니다.
	 *
	 * @param refreshToken 클라이언트가 보유한 유효한 리프레시 토큰
	 * @return 새로 발급된 액세스 토큰 문자열 (JWT 형식)
	 */
	String reissueAccessToken(String refreshToken);

	/**
	 * 사용자 로그인 요청을 처리하고, 인증된 사용자 정보를 반환합니다.
	 * 내부적으로 비밀번호 검증, 회원 상태 체크 등의 로직을 수행합니다.
	 *
	 * @param loginRequest 로그인 요청 DTO (이메일, 비밀번호 등 포함)
	 * @return 인증된 Member 도메인 객체
	 */
	Member login(LoginRequest loginRequest);
}