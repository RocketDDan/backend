package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrewDetailResponse {

	private long crewId;

	private String crewName;

	private String crewAddress;

	private String crewRegion;

	private String profilePath;

	private String introduce;

	private boolean isLeader;

	private boolean isMember;

	private int totalMemberCnt;
}
