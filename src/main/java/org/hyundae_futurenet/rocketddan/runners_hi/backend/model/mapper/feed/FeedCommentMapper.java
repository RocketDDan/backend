package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedCommentMapper {

	@Delete("""
		DELETE FROM FEED_COMMENT
		WHERE FEED_ID = #{feedId}
		""")
	void deleteAll(Long feedId);
}
