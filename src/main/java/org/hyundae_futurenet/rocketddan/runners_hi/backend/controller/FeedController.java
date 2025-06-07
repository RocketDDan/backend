package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedListResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Feed API", description = "Feed")
@RequiredArgsConstructor
@RestController
@RequestMapping("/feeds")
public class FeedController {

	private final FeedFacade feedFacade;

	@Operation(summary = "Feed 조회", description = "Feed를 조회합니다.")
	@GetMapping
	public ResponseEntity<List<FeedListResponse>> searchFeedList(
		@Validated @ModelAttribute FeedSearchFilter feedSearchFilter) {

		long loginMemberId = 1L;
		return ResponseEntity.ok(feedFacade.searchFeedsByFilter(loginMemberId, feedSearchFilter));
	}

	@Operation(summary = "Feed 업로드", description = "Feed를 파일과 함께 업로드합니다.")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> uploadFeed(
		@RequestPart("content") String content,
		@RequestPart(value = "lat", required = false) Double lat,
		@RequestPart(value = "lng", required = false) Double lng,
		@RequestPart("fileList") List<MultipartFile> fileList
	) {

		long loginMemberId = 1L;
		feedFacade.uploadFeed(loginMemberId, content, lat, lng, fileList);

		return ResponseEntity.ok().build();
	}

}
