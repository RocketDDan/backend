package org.hyundae_futurenet.rocketddan.runners_hi.backend.util.payment;

import java.util.HashMap;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.external_dto.response.KakaoPayApproveResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.external_dto.response.KakaoPayReadyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoPayUtil {

	private final RestTemplate restTemplate;

	// properties 파일에서 주입 받도록 처리
	@Value("${kakao-pay.admin-key}")
	private String adminKey;

	@Value("${kakao-pay.success-url}")
	private String successUrl;

	@Value("${kakao-pay.fail-url}")
	private String failUrl;

	@Value("${kakao-pay.cancel-url}")
	private String cancelUrl;

	private static final String CID = "TC0ONETIME";  // 테스트 CID

	private static final String HOST = "https://open-api.kakaopay.com";

	/**
	 * 결제 준비 (Ready API)
	 */
	public KakaoPayReadyResponse kakaoPayReady(
		String partnerOrderId,
		String partnerUserId,
		String itemName,
		int totalAmount) {

		String url = HOST + "/online/v1/payment/ready";

		// 파라미터 생성
		Map<String, String> params = new HashMap<>();
		params.put("cid", CID);
		params.put("partner_order_id", partnerOrderId);
		params.put("partner_user_id", partnerUserId);
		params.put("item_name", itemName);
		params.put("quantity", "1");
		params.put("total_amount", String.valueOf(totalAmount));
		params.put("tax_free_amount", "0");
		params.put("approval_url", successUrl); // 사용자 브라우저가 리다이렉트되는 URL
		params.put("cancel_url", cancelUrl);
		params.put("fail_url", failUrl);

		HttpEntity<Map<String, String>> request = new HttpEntity<>(params, getHeader());

		ResponseEntity<KakaoPayReadyResponse> response = restTemplate.exchange(
			url, HttpMethod.POST, request, KakaoPayReadyResponse.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		}
		throw new IllegalArgumentException(response.toString());
	}

	/**
	 * 결제 승인 (Approve API)
	 */
	public KakaoPayApproveResponse kakaoPayApprove(
		String tid,
		String partnerOrderId,
		long partnerUserId,
		String pgToken) {

		String url = HOST + "/online/v1/payment/approve";

		Map<String, String> params = new HashMap<>();
		params.put("cid", CID);
		params.put("tid", tid);
		params.put("partner_order_id", partnerOrderId);
		params.put("partner_user_id", String.valueOf(partnerUserId));
		params.put("pg_token", pgToken);

		HttpEntity<Map<String, String>> request = new HttpEntity<>(params, getHeader());

		ResponseEntity<KakaoPayApproveResponse> response = restTemplate.exchange(
			url, HttpMethod.POST, request, KakaoPayApproveResponse.class);
		return response.getBody();
	}

	private HttpHeaders getHeader() {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "SECRET_KEY " + adminKey);
		headers.add("Content-Type", "application/json");
		return headers;
	}
}
