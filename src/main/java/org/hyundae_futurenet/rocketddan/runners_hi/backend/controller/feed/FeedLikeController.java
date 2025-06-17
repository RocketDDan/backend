package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller.feed;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
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
	public ResponseEntity<Void> addLike(
		@Auth final Accessor accessor,
		@PathVariable("feed-id") long feedId
	) {

		long loginMemberId = accessor.getMemberId();
		feedFacade.likeFeed(loginMemberId, feedId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Feed 좋아요 취소", description = "Feed에 달린 좋아요를 취소합니다.")
	@DeleteMapping
	public ResponseEntity<Void> removeLike(
		@Auth final Accessor accessor,
		@PathVariable("feed-id") long feedId
	) {

		long loginMemberId = accessor.getMemberId();
		feedFacade.unlikeFeed(loginMemberId, feedId);
		return ResponseEntity.ok().build();
	}
}
