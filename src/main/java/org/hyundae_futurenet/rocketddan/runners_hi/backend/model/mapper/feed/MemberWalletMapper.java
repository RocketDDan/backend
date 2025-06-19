package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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

	@Update("""
		    UPDATE MEMBER_WALLET MW
		         SET MW.BALANCE =\s
		             CASE\s
		                 WHEN MW.CHARGE_AMOUNT - (
		                     SELECT COALESCE(COUNT(*), 0)
		                     FROM FEED_VIEW_LOG FVL
		                     WHERE FVL.FEED_ID = MW.FEED_ID
		                 ) * 10 < 0 THEN 0
		                 ELSE MW.CHARGE_AMOUNT - (
		                     SELECT COALESCE(COUNT(*), 0)
		                     FROM FEED_VIEW_LOG FVL
		                     WHERE FVL.FEED_ID = MW.FEED_ID
		                 ) * 10
		             END
		         WHERE MW.BALANCE > 0
		""")
		// @Update("""
		// 	    WITH VIEW_COUNTS AS (
		// 	        SELECT FEED_ID, COUNT(*) * 10 AS total_deduction
		// 	        FROM FEED_VIEW_LOG
		// 	        GROUP BY FEED_ID
		// 	    )
		// 	    UPDATE MEMBER_WALLET MW
		// 	    SET BALANCE = CASE
		// 	        WHEN MW.CHARGE_AMOUNT - COALESCE(VC.total_deduction, 0) < 0 THEN 0
		// 	        ELSE MW.CHARGE_AMOUNT - COALESCE(VC.total_deduction, 0)
		// 	    END
		// 	    FROM VIEW_COUNTS VC
		// 	    WHERE MW.FEED_ID = VC.FEED_ID
		// 	      AND MW.BALANCE > 0
		// 	""")
	void calculateMemberWalletBalances();
}
