package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller.pay;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.KakaoPayApproveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PayController {

	private final FeedFacade feedFacade;

	/// 카카오페이 결제 승인 -> 성공 시 리다이렉트
	@PostMapping("/kakao/success")
	public ResponseEntity<?> handleKakaoPaySuccess(
		@RequestBody KakaoPayApproveRequest kakaoPayApproveRequest
	) {

		log.info("[결제 승인] : pgToken: {} | partnerOrderId: {}", kakaoPayApproveRequest.getPgToken(),
			kakaoPayApproveRequest.getPartnerOrderId());
		feedFacade.approveFeedPay(kakaoPayApproveRequest);
		return ResponseEntity.ok("success");
	}
}
