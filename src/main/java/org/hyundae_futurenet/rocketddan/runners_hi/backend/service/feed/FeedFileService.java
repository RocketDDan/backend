package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

import java.util.List;

public interface FeedFileService {

	void save(long loginMemberId, long feedId, List<String> uploadedfilePathList);

	List<String> searchFilePathList(long feedId);

	void deleteAll(long loginMemberId, long feedId);
}
