package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.FeedFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedCommentRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedCommentUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.CommentDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		@RequestBody FeedCommentRequest feedCommentRequest) {

		long loginMemberId = 1L;
		feedFacade.registerComment(loginMemberId, feedId, feedCommentRequest.getComment());
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Feed 댓글 수정", description = "Feed 댓글을 수정합니다.")
	@PutMapping("/{comment-id}")
	public ResponseEntity<Void> updateComment(
		@PathVariable("feed-id") long feedId,
		@PathVariable("comment-id") String commentId,
		@RequestBody FeedCommentUpdateRequest feedCommentUpdateRequest
	) {

		long loginMemberId = 1L;
		feedFacade.updateComment(loginMemberId, feedId, commentId, feedCommentUpdateRequest.getNewComment());
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Feed 댓글 삭제", description = "Feed 댓글을 삭제합니다.")
	@DeleteMapping("/{comment-id}")
	public ResponseEntity<Void> deleteComment(
		@PathVariable("feed-id") long feedId,
		@PathVariable("comment-id") String commentId
	) {

		long loginMemberId = 1L;
		feedFacade.deleteCommit(loginMemberId, feedId, commentId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Feed 댓글 가져오기", description = "Feed 댓글 목록을 가져옵니다.")
	@GetMapping
	public ResponseEntity<List<CommentDetailResponse>> getCommentList(
		@PathVariable("feed-id") long feedId
	) {

		long loginMemberId = 1L;
		return ResponseEntity.ok(feedFacade.searchCommentList(loginMemberId, feedId));
	}
}
