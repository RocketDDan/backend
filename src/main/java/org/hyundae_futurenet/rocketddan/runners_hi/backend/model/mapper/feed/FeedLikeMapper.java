package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FeedLikeMapper {

	@Insert("""
		INSERT INTO FEED_LIKE (FEED_LIKE_ID, FEED_ID, CREATED_BY)
		VALUES (SEQ_FEED_LIKE.nextval, #{feedId}, #{loginMemberId})
		""")
	void insert(@Param("loginMemberId") long loginMemberId,
		@Param("feedId") long feedId);

	@Delete("""
		DELETE FROM FEED_LIKE
		WHERE FEED_ID = #{feedId}
		AND CREATED_BY = #{loginMemberId}
		""")
	void delete(@Param("loginMemberId") long loginMemberId,
		@Param("feedId") long feedId);

}
