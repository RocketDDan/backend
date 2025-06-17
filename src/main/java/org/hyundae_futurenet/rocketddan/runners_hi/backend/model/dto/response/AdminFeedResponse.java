package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "관리자 피드 목록 반환 DTO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminFeedResponse {

	@Schema(description = "피드를 작성한 사용자 닉네임", example = "홍길동")
	private String nickname;

	@Schema(description = "피드 ID (식별자)", example = "1")
	private Long feedId;

	@Schema(description = "피드 생성 일시 (yyyy-MM-dd HH:mm:ss)", example = "2025-06-17 10:15:30")
	private String createdAt;

	@Schema(description = "현재 피드의 남은 잔액 (원)", example = "5000")
	private Long balance;

	@Schema(description = "충전된 총 금액 (원)", example = "20000")
	private Long chargeAmount;
}