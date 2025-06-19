package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.log;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FeedViewLogMapper {

	@Insert("""
		INSERT INTO FEED_VIEW_LOG(FEED_VIEW_LOG_ID, FEED_ID, CREATED_BY)
		VALUES (SEQ_FEED_VIEW_LOG.nextval, #{feedId}, #{memberId})
		""")
	void save(
		@Param("feedId") long feedId,
		@Param("memberId") long memberId);

	@Insert("""
		INSERT INTO FEED_VIEW_LOG(FEED_VIEW_LOG_ID, FEED_ID, IP)
		VALUES (SEQ_FEED_VIEW_LOG.nextval, #{feedId}, #{ip})
		""")
	void saveIp(
		@Param("feedId") long feedId,
		@Param("ip") String ip);
}
