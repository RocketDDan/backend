package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyWalletResponse {

	private Long feedId;

	private String createdAt;

	private Long balance;

	private Long chargeAmount;
}

