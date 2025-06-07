package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.FeedListSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;

public interface FeedService {

	List<FeedListSource> searchFeedsByFilter(long loginMemberId, FeedSearchFilter feedSearchFilter);

	long save(long loginMemberId, String content, Double lat, Double lng);

	void delete(long feedId);

	void assertFeedExists(long loginMemberId, long feedId);
}
