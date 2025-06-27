package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.pay;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.KakaoPaySource;

/**
 * 카카오페이 결제 관련 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 * 결제 정보 저장, 조회 및 상태 업데이트 기능을 제공합니다.
 */
public interface KakaoPayService {

	/**
	 * 카카오페이 결제 정보를 저장합니다.
	 *
	 * @param feedId          결제와 연관된 피드 ID
	 * @param tid             카카오페이에서 발급한 거래 고유 번호 (TID)
	 * @param partnerOrderId  가맹점에서 생성한 주문 번호
	 * @param loginMemberId   결제를 요청한 회원 ID
	 * @param chargeAmount    결제 금액
	 */
	void save(long feedId, String tid, String partnerOrderId, long loginMemberId, long chargeAmount);

	/**
	 * partnerOrderId를 기반으로 결제 정보를 조회합니다.
	 *
	 * @param partnerOrderId 가맹점 주문 번호
	 * @return 결제 정보 DTO (KakaoPaySource)
	 */
	KakaoPaySource getPaySource(String partnerOrderId);

	/**
	 * 결제 상태를 업데이트합니다.
	 *
	 * @param tid    카카오페이 거래 ID
	 * @param status 새로운 상태 (예: PENDING, APPROVED 등)
	 */
	void updateStatus(String tid, String status);
}
