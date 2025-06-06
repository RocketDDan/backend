package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.AnnouncementFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

	private final AnnouncementFacade announcementFacade;

	@PostMapping
	public ResponseEntity<Void> createAnnouncement(@Valid @RequestBody AnnouncementCreateRequest request) {
		// 임시 member_id와 role을 1과 USER로 지정 - JWT 토큰에서 member_id와 role를 받아올 예정
		Long memberId = 6L;
		// 테스트
		String role = "ADMIN";
		announcementFacade.createAnnouncement(request, memberId, role);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{announcementId}")
	public ResponseEntity<Void> updateAnnouncement(
		@PathVariable Long announcementId,
		@Valid @RequestBody AnnouncementCreateRequest request
	) {

		Long memberId = 6L;
		String role = "ADMIN";

		announcementFacade.updateAnnouncement(announcementId, request, memberId, role);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{announcementId}")
	public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long announcementId) {

		Long memberId = 1L;
		String role = "USER";
		announcementFacade.deleteAnnouncement(announcementId, memberId, role);
		return ResponseEntity.ok().build();
	}

}
