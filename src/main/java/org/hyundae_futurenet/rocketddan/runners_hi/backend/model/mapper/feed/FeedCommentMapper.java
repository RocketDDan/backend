package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CommentDetailSource;

@Mapper
public interface FeedCommentMapper {

	@Delete("""
		DELETE FROM FEED_COMMENT
		WHERE FEED_ID = #{feedId}
		""")
	void deleteAll(Long feedId);

	@Insert("""
		INSERT INTO FEED_COMMENT(COMMENT_ID, FEED_ID, CONTENT, CREATED_BY) 
		VALUES (SEQ_FEED_COMMENT.nextval, #{feedId}, #{comment}, #{loginMemberId})
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
		SET CONTENT = #{newComment}
		WHERE COMMENT_ID = #{commentId}
		""")
	void update(@Param("commentId") String commentId,
		@Param("newComment") String newComment);

	@Delete("""
		DELETE FROM FEED_COMMENT
		WHERE COMMENT_ID = #{commentId}
		""")
	void delete(@Param("commentId") String commentId);

	@Select("""
			SELECT
				FC.COMMENT_ID AS commentId,
				FC.CONTENT AS content,
				FC.CREATED_BY AS writerId,
				M.NICKNAME AS writerNickname,
				M.PROFILE_PATH AS writerProfilePath,
				TO_CHAR(FC.CREATED_AT, 'YYYY-MM-DD HH24:MI:SS') AS "createdAt",
				TO_CHAR(FC.UPDATED_AT, 'YYYY-MM-DD HH24:MI:SS') AS "updatedAt",
				CASE WHEN FC.UPDATED_AT IS NOT NULL 		THEN 1 ELSE 0 END AS isUpdated,
				CASE WHEN FC.CREATED_BY = #{loginMemberId}  THEN 1 ELSE 0 END AS	isMine
		
			FROM FEED_COMMENT FC
			JOIN MEMBER M ON FC.CREATED_BY = M.MEMBER_ID
			WHERE FC.FEED_ID = #{feedId}
			ORDER BY FC.CREATED_AT
		""")
	List<CommentDetailSource> searchAll(@Param("loginMemberId") long loginMemberId,
		@Param("feedId") long feedId);

}
