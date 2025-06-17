package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberWalletMapper {

	@Insert("""
		INSERT INTO MEMBER_WALLET(
		                          MEMBER_WALLET_ID, 
		                          FEED_ID, 
		                          MEMBER_ID, 
		                          CHARGE_AMOUNT, 
		                          BALANCE, 
		                          CREATED_BY) 
		VALUES (
		        SEQ_MEMBER_WALLET.nextval,
		        #{feedId},
		        #{memberId},
		        #{chargeAmount},
		        #{chargeAmount},
		        #{memberId}
		        )
		""")
	void save(
		@Param("memberId") long memberId,
		@Param("feedId") long feedId,
		@Param("chargeAmount") long chargeAmount);
}
