package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request;

import lombok.Data;

@Data
public class KakaoPayApproveRequest {

	private String pgToken;

	private String partnerOrderId;
}
