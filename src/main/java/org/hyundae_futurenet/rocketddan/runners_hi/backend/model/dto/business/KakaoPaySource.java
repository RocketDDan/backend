package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business;

import lombok.Data;

@Data
public class KakaoPaySource {

	private long kakaoPayId;

	private long feedId;

	private String tid;

	private String partnerOrderId;

	private String partnerUserId;

	private String createdAt;

	private String createdBy;
}
