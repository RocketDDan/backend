package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfoResponse {

	private String email;

	private String nickname;

	private String profilePath;

	private String phone;

	public static MemberInfoResponse from(final Member member) {

		return new MemberInfoResponse(
			member.getEmail(),
			member.getNickname(),
			member.getProfilePath(),
			member.getPhone()
		);
	}
}
