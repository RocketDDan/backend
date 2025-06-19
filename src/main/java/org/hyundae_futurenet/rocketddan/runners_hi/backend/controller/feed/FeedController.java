package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller.feed;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.NotGuest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.feed.FeedListResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Feed API", description = "Feed")
@RequiredArgsConstructor
@RestController
@RequestMapping("/feeds")
public class FeedController {

	private final FeedFacade feedFacade;

	@Operation(summary = "Feed 조회", description = "Feed를 조회합니다.")
	@GetMapping
	public ResponseEntity<List<FeedListResponse>> searchFeedList(
		@Auth final Accessor accessor,
		@Validated @ModelAttribute FeedSearchFilter feedSearchFilter) {

		log.info("FeedController.searchFeedList :: scope: {} | memberId: {}", feedSearchFilter.getScope().name(),
			feedSearchFilter.getMemberId());
		long loginMemberId = accessor.getMemberId();
		return ResponseEntity.ok(feedFacade.searchFeedsByFilter(loginMemberId, feedSearchFilter));
	}

	@NotGuest
	@Operation(summary = "개인의 Feed 업로드", description = "Feed를 파일과 함께 업로드합니다.")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> uploadFeed(
		@Auth final Accessor accessor,
		@RequestParam("content") String content,
		@RequestParam(value = "lat", required = false) Double lat,
		@RequestParam(value = "lng", required = false) Double lng,
		@RequestPart("fileList") List<MultipartFile> fileList
	) {

		long loginMemberId = accessor.getMemberId();
		feedFacade.uploadFeed(loginMemberId, content, lat, lng, fileList);

		return ResponseEntity.ok().build();
	}

	@NotGuest
	@Operation(summary = "Feed 삭제", description = "Feed를 삭제합니다.")
	@DeleteMapping("/{feed-id}")
	public ResponseEntity<Void> deleteFeed(
		@Auth final Accessor accessor,
		@PathVariable("feed-id") long feedId
	) {

		long loginMemberId = accessor.getMemberId();
		feedFacade.deleteFeed(loginMemberId, feedId);

		return ResponseEntity.ok().build();
	}

	@NotGuest
	@Operation(summary = "Feed 수정", description = "Feed를 수정합니다.")
	@PutMapping("/{feed-id}")
	public ResponseEntity<Void> updateFeed(
		@Auth final Accessor accessor,
		@PathVariable("feed-id") long feedId,
		@RequestPart("content") String newContent,
		@RequestPart(value = "lat", required = false) Double newLat,
		@RequestPart(value = "lng", required = false) Double newLng,
		@RequestPart("fileList") List<MultipartFile> newfileList
	) {

		long loginMemberId = accessor.getMemberId();
		feedFacade.updateFeed(loginMemberId, feedId, newContent, newLat, newLng, newfileList);
		return ResponseEntity.ok().build();
	}
}
