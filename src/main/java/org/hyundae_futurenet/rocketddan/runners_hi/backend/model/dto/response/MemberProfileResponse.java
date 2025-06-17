package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberProfileResponse {

	private String email;

	private String nickname;

	private String profileImageUrl;

	private String crewName;

	private boolean isLeader;
}
