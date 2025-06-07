package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FeedFileMapper {

	void insertAll(@Param("loginMemberId") long loginMemberId,
		@Param("feedId") long feedId,
		@Param("uploadedFilePathList") List<String> uploadedFilePathList);
}
