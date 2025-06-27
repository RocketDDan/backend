package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

/**
 * 회원 지갑(Wallet) 관련 금액 처리 로직을 담당하는 서비스 인터페이스입니다.
 * 주로 광고 피드 결제 완료 후 기업 회원의 수익을 반영하는 데 사용됩니다.
 */
public interface MemberWalletService {

	/**
	 * 피드 결제 완료 후, 해당 기업 회원에게 수익을 충전합니다.
	 *
	 * @param feedId 관련 피드 ID
	 * @param memberId 금액을 충전할 회원 ID (기업 회원)
	 * @param chargeAmount 충전할 금액 (예: 결제된 리워드 금액)
	 */
	void setCharge(long feedId, long memberId, long chargeAmount);
}