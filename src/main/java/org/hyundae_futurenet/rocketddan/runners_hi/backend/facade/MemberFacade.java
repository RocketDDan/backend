package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MemberResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.NicknameCheckResponse;

/**
 * 회원(Member) 관련 기능을 담당하는 퍼사드 인터페이스입니다.
 * 이메일 조회, 회원 정보 응답 변환, 닉네임 중복 체크 등을 제공합니다.
 */
public interface MemberFacade {

	/**
	 * 이메일을 기반으로 회원을 조회합니다.
	 *
	 * @param email 회원 이메일
	 * @return 해당 이메일을 가진 Member 객체 (Optional)
	 */
	Optional<Member> findByEmail(String email);

	/**
	 * 회원 ID로 회원 정보를 조회하고, 응답 DTO로 반환합니다.
	 *
	 * @param memberId 조회할 회원 ID
	 * @return MemberResponse DTO
	 */
	MemberResponse findMember(Long memberId);

	/**
	 * 닉네임 중복 여부를 확인합니다.
	 *
	 * @param nickname 확인할 닉네임
	 * @return 중복 여부를 포함한 응답 DTO
	 */
	NicknameCheckResponse existsByNickname(String nickname);
}