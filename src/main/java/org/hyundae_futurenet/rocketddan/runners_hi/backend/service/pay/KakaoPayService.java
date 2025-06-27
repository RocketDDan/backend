package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.pay;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.KakaoPaySource;

public interface KakaoPayService {

	void save(long feedId, String tid, String partnerOrderId, long loginMemberId, long chargeAmount);

	KakaoPaySource getPaySource(String partnerOrderId);
}
