package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.member;

import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.SignUpRequest;

/**
 * 회원 관련 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 * 회원 가입, 프로필 이미지 업데이트, 회원 정보 조회 등의 기능을 제공합니다.
 */
public interface MemberService {

	/**
	 * 이메일을 기준으로 회원 정보를 조회합니다.
	 *
	 * @param email 회원 이메일
	 * @return 해당 이메일을 가진 회원 정보 Optional
	 */
	Optional<Member> findByEmail(String email);

	/**
	 * 닉네임을 기준으로 회원 정보를 조회합니다.
	 *
	 * @param nickname 회원 닉네임
	 * @return 해당 닉네임을 가진 회원 정보 Optional
	 */
	Optional<Member> findByNickname(String nickname);

	/**
	 * 이메일을 기준으로 비밀번호를 조회합니다.
	 * (보안상 실제 사용 시 주의가 필요합니다)
	 *
	 * @param email 회원 이메일
	 * @return 해당 이메일에 해당하는 비밀번호 Optional
	 */
	Optional<String> findPasswordByEmail(String email);

	/**
	 * 회원 가입을 수행하고 생성된 회원의 ID를 반환합니다.
	 *
	 * @param signUpRequest 회원 가입 요청 정보
	 * @return 생성된 회원 ID
	 */
	long signUp(SignUpRequest signUpRequest);

	/**
	 * 회원의 프로필 이미지를 업데이트합니다.
	 *
	 * @param memberId 회원 ID
	 * @param uploadedFilePath 업로드된 이미지 파일 경로
	 */
	void updateProfileImage(long memberId, String uploadedFilePath);

	/**
	 * 회원 ID를 기준으로 회원 정보를 조회합니다.
	 *
	 * @param memberId 회원 ID
	 * @return 회원 정보 Optional
	 */
	Optional<Member> findMember(Long memberId);

	/**
	 * 해당 닉네임이 이미 존재하는지 여부를 반환합니다.
	 *
	 * @param nickname 확인할 닉네임
	 * @return 존재 여부
	 */
	boolean existsByNickname(String nickname);

	/**
	 * 회원 ID를 통해 회원의 이메일을 조회합니다.
	 *
	 * @param memberId 회원 ID
	 * @return 회원 이메일
	 */
	String findMemberEmail(long memberId);
}
