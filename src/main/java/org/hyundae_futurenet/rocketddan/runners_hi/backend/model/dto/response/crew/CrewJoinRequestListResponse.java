package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "크루 가입 요청 목록 조회 응답 DTO")
public record CrewJoinRequestListResponse(
	@Schema(description = "크루 가입 요청 ID", example = "1")
	Long crewJoinRequestId,

	@Schema(description = "회원 ID", example = "1")
	Long memberId,

	@Schema(description = "닉네임", example = "엘모")
	String nickname,

	@Schema(description = "이메일", example = "qlrqod3356@gmail.com")
	String email,

	@Schema(description = "프로필 이미지 경로", example = "profile/image.jpg")
	String profilePath,

	@Schema(description = "가입 요청 일자", example = "2025-06-08 10:00:00")
	String requestDate,

	@Schema(description = "가입 요청 메시지", example = "받아주세요..")
	String requestMessage,

	@Schema(description = "요청 상태", example = "ACCEPT | DENY | REQUEST")
	String status
) {

}

