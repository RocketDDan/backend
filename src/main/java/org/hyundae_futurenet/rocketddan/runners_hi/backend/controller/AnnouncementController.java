package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.MemberAdminOnly;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.NotGuest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.AnnouncementFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * 역할 : ADMIN, USER, COMPANY
 */
@Tag(name = "공지사항 API", description = "공지사항 CRUD 및 조회 기능")
@Slf4j
@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

	private final AnnouncementFacade announcementFacade;

	@Operation(summary = "공지 등록", description = "관리자 또는 크루장이 공지를 등록합니다.")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@MemberAdminOnly
	public ResponseEntity<Void> createAnnouncement(
		@Auth final Accessor accessor,
		@Parameter(description = "공지 제목") @RequestParam("title") String title,
		@Parameter(description = "공지 내용") @RequestParam("content") String content,
		@Parameter(description = "첨부 파일") @RequestPart(value = "files", required = false) List<MultipartFile> files
	) {

		log.info("공지 등록 요청 들어옴");
		log.info("확인 : {}", accessor.getMemberId());
		log.info("권한 확인 : {}", accessor.getAuthority().name());
		announcementFacade.createAnnouncement(title, content, files, accessor.getMemberId(),
			accessor.getAuthority().name());
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "공지 수정", description = "공지 ID를 기반으로 공지사항 내용을 수정합니다.")
	@PutMapping(value = "/{announcementId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@MemberAdminOnly
	public ResponseEntity<Void> updateAnnouncement(
		@Auth final Accessor accessor,
		@Parameter(description = "공지 ID") @PathVariable Long announcementId,
		@Parameter(description = "수정할 제목") @RequestParam("title") String title,
		@Parameter(description = "수정할 내용") @RequestParam("content") String content,
		@Parameter(description = "새 첨부 파일") @RequestPart(value = "files", required = false) List<MultipartFile> files
	) {

		log.info("멤버 확인 : {}", accessor.getMemberId());
		log.info("권한 학안 : {}", accessor.getAuthority().name());

		AnnouncementUpdateRequest request = new AnnouncementUpdateRequest();
		request.setTitle(title);
		request.setContent(content);
		request.setNewFiles(files);

		announcementFacade.updateAnnouncement(announcementId, request, accessor.getMemberId(),
			accessor.getAuthority().name());
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "공지 삭제", description = "공지 ID를 기반으로 공지사항을 삭제합니다.")
	@DeleteMapping("/{announcementId}")
	@MemberAdminOnly
	public ResponseEntity<Void> deleteAnnouncement(
		@Auth final Accessor accessor,
		@Parameter(description = "공지 ID") @PathVariable Long announcementId
	) {

		log.info("확인 : {}", accessor.getMemberId());
		log.info("권한 확인 : {}", accessor.getAuthority().name());
		announcementFacade.deleteAnnouncement(announcementId, accessor.getMemberId(), accessor.getAuthority().name());
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "공지 목록 조회", description = "사용자의 권한에 따라 조회 가능한 공지 목록을 필터링 및 페이징하여 반환합니다.")
	@GetMapping
	public ResponseEntity<AnnouncementListResult> getAnnouncementList(
		@Auth final Accessor accessor,
		@Parameter(description = "공지 범위 필터 (ALL, COMPANY 등)") @RequestParam(required = false) String scope,
		@Parameter(description = "검색 키워드") @RequestParam(required = false) String keyword,
		@Parameter(description = "페이지 번호") @RequestParam(required = false, defaultValue = "1") int page,
		@Parameter(description = "페이지당 항목 수") @RequestParam(required = false, defaultValue = "6") int perPage,
		@Parameter(description = "정렬 순서 (LATEST 등)") @RequestParam(required = false, defaultValue = "LATEST") String order
	) {

		Long memberId = (accessor != null) ? accessor.getMemberId() : null;
		String role = (accessor != null) ? accessor.getAuthority().name() : null;
		log.info("공지 목록 출력");
		log.info("확인 : {}", accessor.getMemberId());
		log.info("권한 확인 : {}", accessor.getAuthority().name());

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

	@Operation(summary = "공지 상세 조회", description = "공지 ID로 상세 정보를 조회합니다.")
	@GetMapping("/{announcementId}")
	@NotGuest
	public ResponseEntity<AnnouncementDetailResponse> getAnnouncementDetail(
		@Auth final Accessor accessor,
		@Parameter(description = "공지 ID") @PathVariable Long announcementId
	) {

		log.info("확인 : {}", accessor.getMemberId());
		log.info("권한 확인 : {}", accessor.getAuthority().name());

		AnnouncementDetailResponse response = announcementFacade.getAnnouncementDetail(announcementId);
		log.info("AnnouncementDetail::Controller={}", response);
		return ResponseEntity.ok(response);
	}

}
