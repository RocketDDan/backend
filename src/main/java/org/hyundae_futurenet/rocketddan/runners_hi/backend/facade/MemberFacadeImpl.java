package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MemberInfoResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.member.MemberService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacadeImpl implements MemberFacade {

	private final MemberService memberService;

	@Override
	public Optional<Member> findByEmail(String email) {

		return memberService.findByEmail(email);
	}

	@Override
	public MemberInfoResponse getPersonalInfo(Long memberId) {

		return memberService.getPersonalInfo(memberId);
	}
}
