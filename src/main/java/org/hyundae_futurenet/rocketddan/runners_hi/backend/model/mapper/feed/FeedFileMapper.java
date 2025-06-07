package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FeedFileMapper {

	void insertAll(@Param("loginMemberId") long loginMemberId,
		@Param("feedId") long feedId,
		@Param("uploadedFilePathList") List<String> uploadedFilePathList);

	@Select("""
		SELECT FILE_PATH
		FROM FEED_FILE
		WHERE FEED_ID = #{feedId}
		""")
	List<String> selectFilePathList(@Param("feedId") Long feedId);

	@Delete("""
		DELETE FROM FEED_FILE
		WHERE CREATED_BY = #{loginMemberId}
		AND
		FEED_ID = #{feedId}
		""")
	void deleteAll(@Param("loginMemberId") long loginMemberId,
		@Param("feedId") Long feedId);
}
