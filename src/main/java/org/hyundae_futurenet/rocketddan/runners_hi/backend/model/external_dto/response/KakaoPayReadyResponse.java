package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.external_dto.response;

import lombok.Data;

@Data
public class KakaoPayReadyResponse {

	private String tid;

	private String partner_order_id;

	private String next_redirect_app_url;

	private String next_redirect_mobile_url;

	private String next_redirect_pc_url;

	private String android_app_scheme;

	private String ios_app_scheme;

	private String created_at;
}