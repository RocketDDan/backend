package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "크루 멤버 목록 조회 응답 DTO")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CrewMemberListResponse {

	@Schema(description = "크루 멤버 ID", example = "1")
	private Long crewMemberId;

	@Schema(description = "회원 ID", example = "1")
	private Long memberId;

	@Schema(description = "닉네임", example = "러닝러버")
	private String nickname;

	@Schema(description = "프로필 이미지 경로", example = "member/profile/1.jpg")
	private String profilePath;

	@Schema(description = "가입 일자", example = "2024-03-20")
	private String registerDate;

	@Schema(description = "크루장 여부", example = "true")
	private boolean isLeader;
}