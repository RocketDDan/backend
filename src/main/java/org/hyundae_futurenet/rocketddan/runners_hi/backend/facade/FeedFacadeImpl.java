package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.converter.FeedListResponseConverter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.FeedListSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed.FeedFileService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed.FeedService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file.S3FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedFacadeImpl implements FeedFacade {

	private final FeedService feedService;

	private final FeedFileService feedFileService;

	private final FeedListResponseConverter feedListResponseConverter;

	private final S3FileUtil s3FileUtil;

	@Override
	public List<FeedListResponse> searchFeedsByFilter(long loginMemberId, FeedSearchFilter feedSearchFilter) {
		// 1. 정보 가져오기
		List<FeedListSource> feedListSources = feedService.searchFeedsByFilter(loginMemberId, feedSearchFilter);
		// 2. filePath -> url 로 변환
		return feedListSources.stream()
			.map(feedListResponseConverter::toDto)
			.toList();
	}

	@Override
	public void uploadFeed(long loginMemberId, String content, Double lat, Double lng, List<MultipartFile> fileList) {
		// feed 테이블에 피드 정보 저장
		long feedId = feedService.save(loginMemberId, content, lat, lng);
		log.info("feedId: {}", feedId);
		// s3 파일 저장소에 피드 파일 저장
		List<String> uploadedfilePathList = s3FileUtil.uploadFeedFile(fileList, feedId);
		// feed_file 테이블에 피드 파일 정보 저장
		feedFileService.save(loginMemberId, feedId, uploadedfilePathList);
	}
}
