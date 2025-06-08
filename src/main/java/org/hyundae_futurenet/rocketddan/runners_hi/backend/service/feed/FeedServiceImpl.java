package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.FeedListSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed.FeedMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

	private final FeedMapper feedMapper;

	@Override
	public List<FeedListSource> searchFeedsByFilter(long loginMemberId, FeedSearchFilter feedSearchFilter) {

		return feedMapper.selectFeedsByFilter(loginMemberId, feedSearchFilter);
	}

	@Override
	public long save(long loginMemberId, String content, Double lat, Double lng) {

		long feedId = feedMapper.getNextFeedId();
		feedMapper.insertFeed(feedId, loginMemberId, content, lat, lng);
		return feedId;
	}

	@Override
	public void delete(long feedId) {

		feedMapper.delete(feedId);
	}

	@Override
	public void assertFeedExists(long loginMemberId, long feedId) {

		if (!feedMapper.validateFeedExistence(loginMemberId, feedId)) {
			throw new IllegalArgumentException("해당 로그인한 유저와 피드 id에 해당하는 피드 데이터가 없습니다.");
		}
	}

	@Override
	public void update(long feedId, String newContent, Double newLat, Double newLng) {

		feedMapper.update(feedId, newContent, newLat, newLng);
	}
}
