package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds/{feed-id}/likes")
public class FeedLikeController {

	private final FeedFacade feedFacade;

	@PostMapping
	public ResponseEntity<Void> addLike(@PathVariable("feed-id") long feedId) {

		long loginMemberId = 1L;
		feedFacade.like(loginMemberId, feedId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> removeLike(@PathVariable("feed-id") long feedId) {

		long loginMemberId = 1L;
		feedFacade.unlike(loginMemberId, feedId);
		return ResponseEntity.ok().build();
	}
}
