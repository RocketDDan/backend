package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.pay;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.KakaoPaySource;

@Mapper
public interface KakaoPayMapper {

	@Insert("""
		INSERT INTO KAKAO_PAY(
		                      KAKAO_PAY_ID,
							  FEED_ID,
							  TID,
							  PARTNER_ORDER_ID,
							  PARTNER_USER_ID,
		                      CHARGE_AMOUNT,
							  CREATED_BY
		 )
		VALUES (
							SEQ_KAKAO_PAY.nextval,
							#{feedId},
							#{tid},
							#{partnerOrderId},
							#{partnerUserId},
		        			#{chargeAmount},
							#{partnerUserId}
		)
		""")
	void save(
		@Param("feedId") long feedId,
		@Param("tid") String tid,
		@Param("partnerOrderId") String partnerOrderId,
		@Param("partnerUserId") long partnerUserId,
		@Param("chargeAmount") long chargeAmount);

	@Select("""
		SELECT
			KAKAO_PAY_ID AS kakaoPayId,
			FEED_ID AS feedId,
			TID AS tid,
			PARTNER_ORDER_ID AS partnerOrderId,
			PARTNER_USER_ID AS partnerUserId,
			CHARGE_AMOUNT AS chargeAmount,
			CREATED_AT AS createdAt,
			CREATED_BY AS createdBy
		    FROM KAKAO_PAY
		WHERE
		    PARTNER_ORDER_ID=#{partnerOrderId}
		""")
	KakaoPaySource selectByPartnerOrderId(@Param("partnerOrderId") String partnerOrderId);
}
