package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.CrewFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequestStatus;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewMemberResignRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewJoinRequestSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewMemberSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewJoinRequestListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewMemberListResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@Operation(summary = "크루 가입 요청", description = "크루에 가입을 요청합니다.")
	@PostMapping("/{crew-id}/join-requests")
	private ResponseEntity<Void> insertCrewJoinRequest(
		@PathVariable("crew-id") Long crewId,
		@Valid @RequestBody CrewJoinRequest crewJoinRequest
	) {

		crewFacade.insertCrewJoinRequest(loginMemberId, crewId, crewJoinRequest);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루 가입 요청 승인", description = "크루 가입 요청의 상태를 승인으로 변경합니다.")
	@PutMapping("/{crew-id}/join-requests/{request-id}/accept")
	private ResponseEntity<Void> acceptCrewJoinRequest(
		@PathVariable("crew-id") Long crewId,
		@PathVariable("request-id") Long crewJoinRequestId
	) {

		crewFacade.updateCrewJoinRequest(loginMemberId, crewId, crewJoinRequestId, CrewJoinRequestStatus.ACCEPT);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루 가입 요청 거절", description = "크루 가입 요청의 상태를 거절로 변경합니다.")
	@PutMapping("/{crew-id}/join-requests/{request-id}/deny")
	private ResponseEntity<Void> denyCrewJoinRequest(
		@PathVariable("crew-id") Long crewId,
		@PathVariable("request-id") Long crewJoinRequestId
	) {

		crewFacade.updateCrewJoinRequest(loginMemberId, crewId, crewJoinRequestId, CrewJoinRequestStatus.DENY);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루 가입 요청 목록 조회", description = "크루의 가입 요청 목록을 조회합니다.")
	@GetMapping("/{crew-id}/join-requests")
	private ResponseEntity<List<CrewJoinRequestListResponse>> selectCrewJoinRequests(
		@PathVariable("crew-id") Long crewId,
		@ModelAttribute CrewJoinRequestSearchFilter crewJoinRequestSearchFilter
	) {

		List<CrewJoinRequestListResponse> result = crewFacade
			.selectCrewJoinRequestsByStatus(loginMemberId, crewId, crewJoinRequestSearchFilter);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "크루 멤버 탈퇴", description = "크루에서 탈퇴합니다.")
	@PostMapping("/{crew-id}/resign")
	private ResponseEntity<Void> deleteCrewMember(
		@PathVariable("crew-id") Long crewId,
		@RequestBody CrewMemberResignRequest request
	) {
		// 사용자 비밀번호 검증 로직 필요

		crewFacade.deleteCrewMember(loginMemberId, crewId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루 멤버 강퇴", description = "크루에서 특정 멤버를 강제 퇴출시킵니다.")
	@DeleteMapping("/{crew-id}/members/{crew-member-id}")
	private ResponseEntity<Void> forcedRemovalCrewMember(
		@PathVariable("crew-id") Long crewId,
		@PathVariable("crew-member-id") Long crewMemberId
	) {

		crewFacade.forcedRemovalCrewMember(loginMemberId, crewId, crewMemberId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루원 목록 조회", description = "크루원 목록을 조회합니다.")
	@GetMapping("/{crew-id}/members")
	private ResponseEntity<List<CrewMemberListResponse>> selectCrewMembers(
		@PathVariable("crew-id") Long crewId,
		@ModelAttribute CrewMemberSearchFilter crewMemberSearchFilter
	) {

		List<CrewMemberListResponse> result = crewFacade.selectCrewMembers(
			loginMemberId,
			crewId,
			crewMemberSearchFilter);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "크루장 변경", description = "크루장이 다른 크루원에게 권한을 넘깁니다.")
	@PutMapping("/{crew-id}/leader/{crew-member-id}")
	private ResponseEntity<Void> changeCrewLeader(
		@PathVariable("crew-id") long crewId,
		@PathVariable("crew-member-id") long crewMemberId) {

		crewFacade.updateCrewMemberIsLeader(loginMemberId, crewId, crewMemberId);
		return ResponseEntity.ok().build();
	}

}