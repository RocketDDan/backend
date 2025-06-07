package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedListResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FeedFacade {

	List<FeedListResponse> searchFeedsByFilter(long loginMemberId, FeedSearchFilter feedSearchFilter);

	void uploadFeed(long loginMemberId, String content, Double lat, Double lng, List<MultipartFile> fileList);

	void deleteFeed(long loginMemberId, long feedId);
}
