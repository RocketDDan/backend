package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "크루 상세 정보 응답 DTO")
@Getter
@Setter
public class CrewDetailResponse {

	@Schema(description = "크루 ID", example = "1")
	private Long crewId;

	@Schema(description = "크루 이름", example = "러닝메이트")
	private String crewName;

	@Schema(description = "크루 주소", example = "서울특별시 강남구 테헤란로 123")
	private String crewAddress;

	@Schema(description = "크루 지역", example = "서울특별시 강남구")
	private String crewRegion;

	@Schema(description = "크루 프로필 이미지 경로", example = "crew/profile/1.jpg")
	private String profilePath;

	@Schema(description = "크루 소개", example = "건강한 러닝 문화를 함께 만들어가요!")
	private String introduce;

	@Schema(description = "크루장 여부", example = "true")
	private boolean isLeader;

	@Schema(description = "크루 멤버 여부", example = "true")
	private boolean isMember;

	@Schema(description = "전체 멤버 수", example = "10")
	private int totalMemberCnt;

	@Schema(description = "크루 생성일", example = "2025-06-05")
	private String createdAt;
}