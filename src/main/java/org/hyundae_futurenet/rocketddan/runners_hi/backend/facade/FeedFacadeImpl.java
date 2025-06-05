package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.converter.FeedListResponseConverter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.FeedListSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed.FeedService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedFacadeImpl implements FeedFacade {

	private final FeedService feedService;

	private final FeedListResponseConverter feedListResponseConverter;

	@Override
	public List<FeedListResponse> searchFeedsByFilter(long loginMemberId, FeedSearchFilter feedSearchFilter) {
		// 1. 정보 가져오기
		List<FeedListSource> feedListSources = feedService.searchFeedsByFilter(loginMemberId, feedSearchFilter);
		// 2. filePath -> url 로 변환
		return feedListSources.stream()
			.map(feedListResponseConverter::toDto)
			.toList();
	}
}
