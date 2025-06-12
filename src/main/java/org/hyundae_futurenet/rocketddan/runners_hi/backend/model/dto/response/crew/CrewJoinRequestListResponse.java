package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "크루 가입 요청 목록 조회 응답 DTO")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CrewJoinRequestListResponse {

	@Schema(description = "크루 가입 요청 ID", example = "1")
	private Long crewJoinRequestId;

	@Schema(description = "회원 ID", example = "1")
	private Long memberId;

	@Schema(description = "닉네임", example = "엘모")
	private String nickname;

	@Schema(description = "이메일", example = "qlrqod3356@gmail.com")
	private String email;

	@Schema(description = "프로필 이미지 경로", example = "profile/image.jpg")
	private String profilePath;

	@Schema(description = "가입 요청 일자", example = "2025-06-08 10:00:00")
	private String requestDate;

	@Schema(description = "가입 요청 메시지", example = "받아주세요..")
	private String requestMessage;

	@Schema(description = "요청 상태", example = "ACCEPT | DENY | REQUEST")
	private String status;
}
