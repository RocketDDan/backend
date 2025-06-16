package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.member;

import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.member.MemberException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.SignUpRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.member.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;

	private final PasswordEncoder passwordEncoder;

	@Override
	public Optional<Member> findByEmail(String email) {

		return memberMapper.findByEmail(email);
	}

	@Override
	public Optional<Member> findByNickname(String nickname) {

		return memberMapper.findByNickname(nickname);
	}

	@Override
	public Optional<Member> findMember(Long memberId) {

		return memberMapper.findById(memberId);
	}

	@Override
	public Optional<String> findPasswordByEmail(String email) {

		return memberMapper.findPasswordByEmail(email);
	}

	@Override
	public long signUp(SignUpRequest signUpRequest) {

		if (findByNickname(signUpRequest.getNickname()).isPresent()) {
			throw new MemberException(ErrorCode.SIGNUP_MEMBER_NICKNAME_DUPLICATED);
		}

		Member member = signUpRequest.toMember(passwordEncoder);
		memberMapper.insertMember(member);
		return member.getMemberId();
	}

	@Override
	public void updateProfileImage(long memberId, String uploadedFilePath) {

		memberMapper.updateProfileImage(memberId, uploadedFilePath);
	}

	@Override
	public boolean existsByNickname(String nickname) {

		return memberMapper.existsByNickname(nickname);
	}

	@Override
	public String findMemberEmail(long memberId) {

		return memberMapper.findById(memberId)
			.orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_EXIST))
			.getEmail();
	}
}
