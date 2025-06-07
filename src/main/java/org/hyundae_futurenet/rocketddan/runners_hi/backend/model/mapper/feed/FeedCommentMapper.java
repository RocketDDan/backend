package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

	@Select("""
		SELECT CASE
			WHEN EXISTS (
			  SELECT 1
			  FROM FEED_COMMENT
			  WHERE CREATED_BY = #{loginMemberId}
				AND FEED_ID = #{feedId}
			  	AND COMMENT_ID = #{commentId}
			)
			THEN 1
			ELSE 0
			END
			FROM DUAL
		""")
	boolean validateCommentExistence(
		@Param("loginMemberId") long loginMemberId,
		@Param("feedId") long feedId,
		@Param("commentId") String commentId);

	@Update("""
		
		UPDATE FEED_COMMENT
		SET CONTENT = #{newContent}
		WHERE COMMENT_ID = #{commentId}
		""")
	void update(@Param("commentId") String commentId,
		@Param("newComment") String newComment);

	@Delete("""
		DELETE FROM FEED_COMMENT
		WHERE COMMENT_ID = #{commentId}
		""")
	void delete(@Param("commentId") String commentId);
}
