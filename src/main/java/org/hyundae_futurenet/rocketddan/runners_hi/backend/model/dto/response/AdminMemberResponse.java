package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMemberResponse {

	private String nickname;

	private String email;

	private String crewName;

	private String crewRole;
}
