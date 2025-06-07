package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Feed Comment API", description = "Feed Comment")
@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds/{feed-id}/comments")
public class FeedCommentController {

	private final FeedFacade feedFacade;

	@Operation(summary = "Feed에 댓글 달기", description = "Feed에 댓글을 추가합니다.")
	@PostMapping
	public ResponseEntity<Void> addComment(
		@PathVariable("feed-id") long feedId,
		@RequestParam String comment) {

		long loginMemberId = 1L;
		feedFacade.registerComment(loginMemberId, feedId, comment);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Feed 댓글 수정", description = "Feed 댓글을 수정합니다.")
	@PutMapping("/{comment-id}")
	public ResponseEntity<Void> updateComment(
		@PathVariable("feed-id") long feedId,
		@PathVariable("comment-id") String commentId,
		@RequestParam String newComment
	) {

		long loginMemberId = 1L;
		feedFacade.updateComment(loginMemberId, feedId, commentId, newComment);
		return ResponseEntity.ok().build();
	}
}
