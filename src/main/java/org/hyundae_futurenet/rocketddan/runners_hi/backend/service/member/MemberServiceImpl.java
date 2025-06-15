package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.member;

import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.exception.member.MemberException;
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
	public long signUp(SignUpRequest signUpRequest) {

		if (findByNickname(signUpRequest.getNickname()).isPresent()) {
			throw new MemberException("이미 존재하는 닉네임입니다.");
		}

		Member member = signUpRequest.toMember(passwordEncoder);
		memberMapper.insertMember(member);
		return member.getMemberId();
	}

	@Override
	public void updateProfileImage(long memberId, String uploadedFilePath) {

		memberMapper.updateProfileImage(memberId, uploadedFilePath);
	}
}
