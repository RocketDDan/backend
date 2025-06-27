package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.SignUpRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * OAuth2 기반 회원가입 로직을 담당하는 퍼사드 인터페이스입니다.
 * 소셜 로그인 이후 추가 정보 입력 및 프로필 이미지를 포함한 회원 가입을 처리합니다.
 */
public interface OAuth2Facade {

	/**
	 * OAuth2 소셜 로그인 이후, 추가 정보를 입력받아 회원가입을 처리합니다.
	 *
	 * @param signUpRequest 닉네임, 전화번호, 이메일 등 회원가입에 필요한 정보가 포함된 요청 DTO
	 * @param profileImage 프로필 이미지 파일 (선택 사항)
	 */
	void signUp(SignUpRequest signUpRequest, MultipartFile profileImage);
}