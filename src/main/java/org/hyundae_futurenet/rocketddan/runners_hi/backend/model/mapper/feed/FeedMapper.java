package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.FeedListSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;

@Mapper
public interface FeedMapper {

	List<FeedListSource> selectFeedsByFilter(long loginMemberId, FeedSearchFilter feedSearchFilter);

	@Select("SELECT SEQ_FEED.NEXTVAL FROM DUAL")
	long getNextFeedId();

	@Insert("""
		INSERT INTO FEED (
		        FEED_ID,
		        CONTENT,
				LAT,
				LNG,
		        CREATED_BY
		    ) VALUES (
		        #{feedId},
		        #{content},
		        #{lat},
		        #{lng},
		        #{loginMemberId}
		    )
		""")
	void insertFeed(@Param("feedId") long feedId,
		@Param("loginMemberId") long loginMemberId,
		@Param("content") String content,
		@Param("lat") Double lat,
		@Param("lng") Double lng);

	@Delete("""
		DELETE FROM FEED
		WHERE FEED_ID = #{feedId}
		""")
	void delete(@Param("feedId") Long feedId);

	@Select("""
		SELECT CASE
			WHEN EXISTS (
			  SELECT 1
			  FROM FEED
			  WHERE CREATED_BY = #{loginMemberId}
				AND FEED_ID = #{feedId}
			)
			THEN 1
			ELSE 0
			END
			FROM DUAL
		""")
	boolean validateFeedExistence(long loginMemberId, Long feedId);

	@Update("""
		UPDATE FEED SET CONTENT = #{newContent},
		                LAT = #{newLat},
		                LNG = #{newLng}
		WHERE FEED_ID = #{feedId}
		""")
	void update(@Param("feedId") long feedId,
		@Param("newContent") String newContent,
		@Param("newLat") Double newLat,
		@Param("newLng") Double newLng);
}
