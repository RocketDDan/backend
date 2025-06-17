package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.external_dto.response;

import lombok.Data;

@Data
public class KakaoPayApproveResponse {

	private String aid;

	private String tid;

	private String cid;

	private String partner_order_id;

	private String partner_user_id;

	private String payment_method_type;

	private Amount amount;

	private String approved_at;

	@Data
	public static class Amount {

		private int total;

		private int tax_free;

		private int vat;

		private int point;

		private int discount;
	}
}