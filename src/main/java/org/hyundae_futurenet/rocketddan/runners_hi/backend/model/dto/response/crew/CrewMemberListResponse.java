package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "크루 멤버 목록 조회 응답 DTO")
public record CrewMemberListResponse(
	@Schema(description = "회원 ID", example = "1")
	Long memberId,

	@Schema(description = "닉네임", example = "러닝러버")
	String nickname,

	@Schema(description = "이메일", example = "running@example.com")
	String email,

	@Schema(description = "프로필 이미지 경로", example = "member/profile/1.jpg")
	String profilePath,

	@Schema(description = "가입 일자", example = "2024-03-20 14:30:00")
	String registerDate,

	@Schema(description = "크루장 여부", example = "true")
	boolean isLeader
) {

}