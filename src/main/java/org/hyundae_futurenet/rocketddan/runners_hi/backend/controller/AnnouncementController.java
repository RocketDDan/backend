package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.AnnouncementFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AnnouncementListResult;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> createAnnouncement(
		@RequestParam("title") String title,
		@RequestParam("content") String content,
		@RequestPart(value = "files", required = false) List<MultipartFile> files
	) {

		log.info("공지 등록 요청 들어옴");

		// 임시 memberId, role
		Long memberId = 6L;
		String role = "ADMIN";

		announcementFacade.createAnnouncement(title, content, files, memberId, role);
		return ResponseEntity.ok().build();
	}

	@PutMapping(value = "/{announcementId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> updateAnnouncement(
		@PathVariable Long announcementId,
		@RequestParam("title") String title,
		@RequestParam("content") String content,
		@RequestPart(value = "files", required = false) List<MultipartFile> files
	) {

		Long memberId = 6L;
		String role = "ADMIN";

		AnnouncementUpdateRequest request = new AnnouncementUpdateRequest();
		request.setTitle(title);
		request.setContent(content);
		request.setNewFiles(files);

		announcementFacade.updateAnnouncement(announcementId, request, memberId, role);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{announcementId}")
	public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long announcementId) {

		Long memberId = 6L;
		String role = "ADMIN";
		announcementFacade.deleteAnnouncement(announcementId, memberId, role);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<AnnouncementListResult> getAnnouncementList(
		@RequestParam(required = false) String scope,
		@RequestParam(required = false) String keyword,
		@RequestParam(required = false, defaultValue = "1") int page,
		@RequestParam(required = false, defaultValue = "6") int perPage,
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

		List<AnnouncementListResponse> list = announcementFacade.getAnnouncementList(params, memberId, role);
		int totalCount = announcementFacade.getAnnouncementTotalCount(params, memberId, role);
		AnnouncementListResult result = new AnnouncementListResult(list, totalCount);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{announcementId}")
	public ResponseEntity<AnnouncementDetailResponse> getAnnouncementDetail(@PathVariable Long announcementId) {

		AnnouncementDetailResponse response = announcementFacade.getAnnouncementDetail(announcementId);
		log.info("AnnouncementDetail::Controller={}", response);
		return ResponseEntity.ok(response);
	}

}
