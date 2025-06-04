package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
@Tag(name = "Feed API", description = "Feed")
public class FeedController {

	@Operation(summary = "Feed 조회", description = "Feed를 조회합니다.")
	@GetMapping
	public ResponseEntity<?> searchFeedList() {
		// TODO : 개발하기
		return ResponseEntity.ok("피드 목록");
	}

	@Operation(summary = "Feed 상세 조회", description = "Feed를 조회합니다.")
	@GetMapping("/{feed-id}")
	public ResponseEntity<?> searchFeedDetail(@PathVariable("feed-id") String feedId) {
		// TODO : 개발하기
		return ResponseEntity.ok("피드 상세");
	}
}
