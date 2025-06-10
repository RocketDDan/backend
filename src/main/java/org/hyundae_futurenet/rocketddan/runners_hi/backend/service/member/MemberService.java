package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.member;

import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.SignUpRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MemberInfoResponse;

public interface MemberService {

	Optional<Member> findByEmail(String email);

	Optional<Member> findByNickname(String nickname);

	long signUp(SignUpRequest signUpRequest);

	void updateProfileImage(long memberId, String uploadedFilePath);

	MemberInfoResponse getPersonalInfo(Long memberId);
}
