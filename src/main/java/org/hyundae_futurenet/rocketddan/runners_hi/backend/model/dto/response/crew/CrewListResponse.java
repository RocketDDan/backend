package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "크루 목록 조회 응답 DTO")
public record CrewListResponse(
	@Schema(description = "크루 ID", example = "1")
	Long crewId,

	@Schema(description = "크루 이름", example = "러닝메이트")
	String crewName,

	@Schema(description = "크루 주소", example = "서울특별시 강남구 테헤란로 123")
	String crewAddress,

	@Schema(description = "크루 지역", example = "서울특별시 강남구")
	String crewRegion,

	@Schema(description = "크루 프로필 이미지 경로", example = "crew/profile/1.jpg")
	String profilePath,

	@Schema(description = "전체 멤버 수", example = "10")
	int totalMemberCnt
) {

}