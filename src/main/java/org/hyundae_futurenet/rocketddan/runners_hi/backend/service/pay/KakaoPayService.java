package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.pay;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.KakaoPayApproveRequest;

public interface KakaoPayService {

	void save(long feedId, String tid, String partnerOrderId, long loginMemberId);

	void getPaySource(KakaoPayApproveRequest kakaoPayApproveRequest);
}
