package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller.crew;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.NotGuest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.CrewFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewCreateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewUpdateRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewMemberSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/crews")
@RequiredArgsConstructor
@Tag(name = "CREW API", description = "Crew")
@Slf4j
public class CrewController {

	private final CrewFacade crewFacade;

	@Operation(summary = "크루 생성", description = "새로운 크루를 생성합니다.")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@NotGuest
	public ResponseEntity<Long> createCrew(
		@RequestPart(value = "crew") CrewCreateRequest crewCreateRequest,
		@RequestPart(value = "profile", required = false) MultipartFile profile,
		@Auth final Accessor accessor
	) {

		Long crewId = crewFacade.insertCrew(accessor.getMemberId(), crewCreateRequest, profile);
		return ResponseEntity.ok(crewId);
	}

	@Operation(summary = "크루 수정", description = "크루 정보를 수정합니다.")
	@PutMapping(value = "/{crew-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@NotGuest
	public ResponseEntity<Void> updateCrew(
		@PathVariable("crew-id") Long crewId,
		@RequestPart(value = "crew") CrewUpdateRequest crewUpdateRequest,
		@RequestPart(value = "profile", required = false) MultipartFile profile,
		@Auth final Accessor accessor
	) {

		crewFacade.updateCrew(accessor.getMemberId(), crewId, crewUpdateRequest, profile);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루 삭제", description = "크루를 삭제합니다.")
	@DeleteMapping("/{crew-id}")
	@NotGuest
	public ResponseEntity<Void> deleteCrew(
		@PathVariable("crew-id") Long crewId,
		@Auth final Accessor accessor) {

		crewFacade.deleteCrew(accessor.getMemberId(), crewId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루 프로필 조회", description = "크루를 조회합니다.")
	@GetMapping("/{crew-id}")
	public ResponseEntity<CrewDetailResponse> selectCrew(
		@PathVariable("crew-id") Long crewId,
		@Auth final Accessor accessor
	) {

		Long loginMemberId = accessor != null ? accessor.getMemberId() : null;
		CrewDetailResponse result = crewFacade.selectCrewByCrewId(loginMemberId, crewId);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "크루 목록 조회", description = "필터 조건에 맞는 크루 목록을 조회합니다.")
	@GetMapping
	public ResponseEntity<List<CrewListResponse>> selectCrews(
		@Valid @ModelAttribute CrewSearchFilter crewSearchFilter,
		@Auth final Accessor accessor) {

		if (crewSearchFilter == null) {
			throw new IllegalArgumentException("조회 옵션은 필수입니다.");
		}

		Long loginMemberId = accessor != null ? accessor.getMemberId() : null;
		List<CrewListResponse> result = crewFacade.selectCrewsByFilter(loginMemberId, crewSearchFilter);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "크루 멤버 탈퇴", description = "크루에서 탈퇴합니다.")
	@PostMapping("/{crew-id}/resign")
	@NotGuest
	public ResponseEntity<Void> deleteCrewMember(
		@PathVariable("crew-id") Long crewId,
		@Auth final Accessor accessor) {
		// 사용자 비밀번호 검증 로직 필요

		crewFacade.deleteCrewMember(accessor.getMemberId(), crewId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루 멤버 강퇴", description = "크루에서 특정 멤버를 강제 퇴출시킵니다.")
	@DeleteMapping("/{crew-id}/members/{crew-member-id}")
	@NotGuest
	public ResponseEntity<Void> forcedRemovalCrewMember(
		@PathVariable("crew-id") Long crewId,
		@PathVariable("crew-member-id") Long crewMemberId,
		@Auth final Accessor accessor
	) {

		crewFacade.forcedRemovalCrewMember(accessor.getMemberId(), crewId, crewMemberId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루원 목록 조회", description = "닉네임 검색 가능합니다. 크루장은 첫번째 고정입니다.")
	@GetMapping("/{crew-id}/members")
	public ResponseEntity<List<CrewMemberListResponse>> selectCrewMembers(
		@PathVariable("crew-id") Long crewId,
		@ModelAttribute CrewMemberSearchFilter crewMemberSearchFilter,
		@Auth final Accessor accessor
	) {

		List<CrewMemberListResponse> result = crewFacade.selectCrewMembers(
			accessor.getMemberId(),
			crewId,
			crewMemberSearchFilter);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "크루장 변경", description = "크루장이 다른 크루원에게 권한을 넘깁니다.")
	@PutMapping("/{crew-id}/leader/{crew-member-id}")
	@NotGuest
	public ResponseEntity<Void> changeCrewLeader(
		@PathVariable("crew-id") long crewId,
		@PathVariable("crew-member-id") long crewMemberId,
		@Auth final Accessor accessor) {

		crewFacade.updateCrewMemberIsLeader(accessor.getMemberId(), crewId, crewMemberId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "내 크루 조회", description = "내가 속한 크루 반환")
	@GetMapping("/me")
	public ResponseEntity<Long> selectMyCrew(@Auth final Accessor accessor) {

		Long result = crewFacade.selectMyCrew(accessor.getMemberId());
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "크루 이름 중복 조회", description = "크루 이름 중복 여부 조회")
	@GetMapping("/duplicate")
	public ResponseEntity<Boolean> selectDuplicateCrew(@RequestParam("crewName") String crewName) {

		if (crewName == null || crewName.isBlank()) {
			throw new IllegalArgumentException("크루명은 필수입니다.");
		}

		Boolean result = crewFacade.existsByCrewName(crewName);
		return ResponseEntity.ok(result);
	}

}