package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MemberResponse;

public interface MemberFacade {

	Optional<Member> findByEmail(String email);

	MemberResponse findMember(Long memberId);
}
