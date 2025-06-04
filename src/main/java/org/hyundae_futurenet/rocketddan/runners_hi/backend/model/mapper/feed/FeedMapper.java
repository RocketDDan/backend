package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedListResponse;

@Mapper
public interface FeedMapper {

	// TODO : 임시 메서드 (삭제하고 사용하세요.)
	@Select("""
		    SELECT *
		    FROM TEST_FEED
		""")
	List<FeedListResponse> findAll();
}
