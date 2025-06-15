package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant.Role;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

	private Long memberId;

	private String email;

	private String nickname;

	private String profileImageUrl;

	private Role role;

	public static MemberResponse from(final Member member, final String profileImageUrl) {

		return new MemberResponse(
			member.getMemberId(),
			member.getEmail(),
			member.getNickname(),
			profileImageUrl,
			member.getRole()
		);
	}
}
