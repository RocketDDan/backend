package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyWalletResponse {

	@Schema(description = "피드 ID", example = "102")
	private Long feedId;

	@Schema(description = "피드 생성 일시 (yyyy-MM-dd HH:mm:ss)", example = "2025-06-17 14:30:00")
	private String createdAt;

	@Schema(description = "남은 잔액 (원)", example = "3500")
	private Long balance;

	@Schema(description = "총 충전 금액 (원)", example = "10000")
	private Long chargeAmount;
}

