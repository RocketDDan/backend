package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.CrewFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/crews")
@RequiredArgsConstructor
@Tag(name = "CREW API", description = "Crew")
public class CrewController {

	private final CrewFacade crewFacade;

	private final long loginMemberId = 1L; // 임시

	@Operation(summary = "Crew 생성", description = "새로운 Crew를 생성합니다.")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	private ResponseEntity<Long> createCrew(
		@RequestPart(value = "crew") CrewCreateRequest crewCreateRequest,
		@RequestPart(value = "profile", required = false) MultipartFile profile
	) {

		Long crewId = crewFacade.insertCrew(loginMemberId, crewCreateRequest, profile);
		return ResponseEntity.ok(crewId);
	}

	@Operation(summary = "Crew 수정", description = "Crew 정보를 수정합니다.")
	@PutMapping(value = "/{crew-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	private ResponseEntity<Void> updateCrew(
		@PathVariable("crew-id") Long crewId,
		@RequestPart(value = "crew") CrewUpdateRequest crewUpdateRequest,
		@RequestPart(value = "profile", required = false) MultipartFile profile
	) {

		crewFacade.updateCrew(loginMemberId, crewId, crewUpdateRequest, profile);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Crew 삭제", description = "Crew를 삭제합니다.")
	@DeleteMapping("/{crew-id}")
	private ResponseEntity<Void> deleteCrew(@PathVariable("crew-id") Long crewId) {

		crewFacade.deleteCrew(loginMemberId, crewId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Crew 프로필 조회", description = "Crew를 조회합니다.")
	@GetMapping("/{crew-id}")
	private ResponseEntity<CrewDetailResponse> selectCrew(@PathVariable("crew-id") Long crewId) {

		CrewDetailResponse result = crewFacade.selectCrewByCrewId(loginMemberId, crewId);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "Crew 목록 조회", description = "필터 조건에 맞는 Crew 목록을 조회합니다.")
	@GetMapping
	private ResponseEntity<List<CrewListResponse>> selectCrews(
		@Valid @ModelAttribute CrewSearchFilter crewSearchFilter) {

		List<CrewListResponse> result = crewFacade.selectCrewsByFilter(loginMemberId, crewSearchFilter);
		return ResponseEntity.ok(result);
	}
}