package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.Objects;
import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.exception.member.MemberException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MemberResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.NicknameCheckResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.member.MemberService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file.CloudFrontFileUtil;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacadeImpl implements MemberFacade {

	private final MemberService memberService;

	private final CloudFrontFileUtil cloudFrontFileUtil;

	@Override
	public Optional<Member> findByEmail(String email) {

		return memberService.findByEmail(email);
	}

	@Override
	public MemberResponse findMember(Long memberId) {

		Member member = memberService.findMember(memberId)
			.orElseThrow(() -> new MemberException("존재하지 않는 회원입니다."));
		String profileImageUrl = Objects.nonNull(member.getProfilePath()) ?
			cloudFrontFileUtil.generateSignedUrl(member.getProfilePath(), 60 * 10) : null;
		return MemberResponse.from(member, profileImageUrl);
	}

	@Override
	public NicknameCheckResponse existsByNickname(String nickname) {

		return new NicknameCheckResponse(memberService.existsByNickname(nickname));
	}
}
