package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FeedCommentMapper {

	@Delete("""
		DELETE FROM FEED_COMMENT
		WHERE FEED_ID = #{feedId}
		""")
	void deleteAll(Long feedId);

	@Insert("""
		INSERT INTO FEED_COMMENT(COMMENT_ID, FEED_ID, CONTENT, CREATED_BY) 
		VALUES (SEQ_FEED_COMMENT.nextval, #{feedId}, #{content}, #{loginMemberId})
		""")
	void insert(@Param("loginMemberId") long loginMemberId,
		@Param("feedId") long feedId,
		@Param("comment") String comment);
}
