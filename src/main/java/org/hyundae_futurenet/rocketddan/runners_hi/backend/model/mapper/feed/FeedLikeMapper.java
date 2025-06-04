package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FeedLikeMapper {

	// TODO : 임시 메서드 (삭제하고 사용하세요.)
	@Select("""
		    SELECT *
		    FROM FEED_LIKE
		""")
	List<Object> findAll();
}
