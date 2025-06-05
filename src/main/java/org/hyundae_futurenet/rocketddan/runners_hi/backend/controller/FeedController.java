package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.FeedListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

		return ResponseEntity.ok(feedFacade.searchFeedsByFilter(feedSearchFilter));
	}

}
