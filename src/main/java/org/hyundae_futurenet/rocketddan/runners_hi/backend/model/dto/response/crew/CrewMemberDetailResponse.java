package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "크루 멤버 상세 정보 응답 DTO")
public record CrewMemberDetailResponse(
	@Schema(description = "크루 멤버 ID", example = "1")
	Long crewMemberId,

	@Schema(description = "회원 ID", example = "1")
	Long memberId,

	@Schema(description = "크루장 여부", example = "true")
	boolean isLeader,

	@Schema(description = "가입 일자", example = "2024-03-20 14:30:00")
	String createdAt
) {

}