package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.AnnouncementFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * 역할 : ADMIN, USER, COMPANY
 */
@Slf4j
@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

	private final AnnouncementFacade announcementFacade;

	@PostMapping
	public ResponseEntity<Void> createAnnouncement(@Valid @RequestBody AnnouncementCreateRequest request) {

		log.info("post announcement 호출");
		// 임시 member_id와 role 지정
		Long memberId = 6L;
		String role = "ADMIN";

		announcementFacade.createAnnouncement(request, memberId, role);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{announcementId}")
	public ResponseEntity<Void> updateAnnouncement(
		@PathVariable Long announcementId,
		@Valid @RequestBody AnnouncementUpdateRequest request
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

	@GetMapping
	public ResponseEntity<List<AnnouncementListResponse>> getAnnouncementList(
		@RequestParam(required = false) String scope,
		@RequestParam(required = false) String keyword,
		@RequestParam(required = false, defaultValue = "1") int page,
		@RequestParam(required = false, defaultValue = "10") int perPage,
		@RequestParam(required = false, defaultValue = "LATEST") String order
	) {

		Long memberId = 1L;
		String role = "USER";

		Map<String, Object> params = new HashMap<>();
		if (scope != null) {
			params.put("scope", scope);
		}
		if (keyword != null) {
			params.put("keyword", keyword);
		}
		params.put("keyword", keyword);
		params.put("offset", (page - 1) * perPage);
		params.put("perPage", perPage);
		params.put("order", order);

		List<AnnouncementListResponse> response = announcementFacade.getAnnouncementList(params, memberId, role);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{announcementId}")
	public ResponseEntity<AnnouncementDetailResponse> getAnnouncementDetail(@PathVariable Long announcementId) {

		AnnouncementDetailResponse response = announcementFacade.getAnnouncementDetail(announcementId);
		return ResponseEntity.ok(response);
	}

}
