package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller.feed;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.NotGuest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/view-logs/feeds")
@RequiredArgsConstructor
public class FeedViewLogController {

	private final FeedFacade feedFacade;

	@PostMapping("/{feed-id}")
	@NotGuest
	public ResponseEntity<?> postFeedViewLog(
		@Auth Accessor accessor,
		@PathVariable("feed-id") long feedId
	) {

		feedFacade.addViewLog(accessor.getMemberId(), feedId);
		return ResponseEntity.ok().build();
	}
}
