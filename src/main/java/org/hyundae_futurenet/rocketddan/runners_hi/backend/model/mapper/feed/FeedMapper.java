package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}
