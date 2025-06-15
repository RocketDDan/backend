package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.member;

import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.SignUpRequest;

public interface MemberService {

	Optional<Member> findByEmail(String email);

	Optional<Member> findByNickname(String nickname);

	long signUp(SignUpRequest signUpRequest);

	void updateProfileImage(long memberId, String uploadedFilePath);

	Optional<Member> findMember(Long memberId);

	boolean existsByNickname(String nickname);
}
