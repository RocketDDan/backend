package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Feed Like API", description = "Feed Like")
@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds/{feed-id}/likes")
public class FeedLikeController {

	private final FeedFacade feedFacade;

	@Operation(summary = "Feed 좋아요", description = "Feed에 좋아요를 추가합니다.")
	@PostMapping
	public ResponseEntity<Void> addLike(@PathVariable("feed-id") long feedId) {

		long loginMemberId = 1L;
		feedFacade.likeFeed(loginMemberId, feedId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Feed 좋아요 취소", description = "Feed에 달린 좋아요를 취소합니다.")
	@DeleteMapping
	public ResponseEntity<Void> removeLike(@PathVariable("feed-id") long feedId) {

		long loginMemberId = 1L;
		feedFacade.unlikeFeed(loginMemberId, feedId);
		return ResponseEntity.ok().build();
	}
}
