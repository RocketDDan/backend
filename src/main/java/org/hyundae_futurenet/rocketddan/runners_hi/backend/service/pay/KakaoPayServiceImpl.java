package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.pay;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.KakaoPaySource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.pay.KakaoPayMapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.payment.KakaoPayUtil;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoPayServiceImpl implements KakaoPayService {

	private final KakaoPayUtil kakaoPayUtil;

	private final KakaoPayMapper kakaoPayMapper;

	@Override
	public void save(long feedId, String tid, String partnerOrderId, long partnerUserId, long chargeAmount) {

		kakaoPayMapper.save(feedId, tid, partnerOrderId, partnerUserId, chargeAmount);
	}

	/// db 에서 kakao pay 정보 가져오기
	@Override
	public KakaoPaySource getPaySource(String partnerOrderId) {

		return kakaoPayMapper.selectByPartnerOrderId(partnerOrderId);
	}

	@Override
	public void updateStatus(String tid, String status) {

		kakaoPayMapper.updateStatus(tid, status);
	}
}
