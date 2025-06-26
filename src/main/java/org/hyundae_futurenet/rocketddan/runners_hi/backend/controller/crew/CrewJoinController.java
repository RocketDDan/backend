package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller.crew;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.NotGuest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.CrewFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.CrewJoinRequestStatus;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.crew.filter.CrewJoinRequestSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewJoinRequestListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/crews/{crew-id}/join-requests")
@RequiredArgsConstructor
@Tag(name = "CREW Join Request API", description = "Crew Join Request 요청")
@Slf4j
public class CrewJoinController {

	private final CrewFacade crewFacade;

	@Operation(summary = "크루 가입 요청", description = "크루에 가입을 요청합니다.")
	@PostMapping
	@NotGuest
	public ResponseEntity<Void> insertCrewJoinRequest(
		@PathVariable("crew-id") Long crewId,
		@Valid @RequestBody CrewJoinRequest crewJoinRequest,
		@Auth final Accessor accessor
	) {

		crewFacade.insertCrewJoinRequest(accessor.getMemberId(), crewId, crewJoinRequest);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루 가입 요청 승인", description = "크루 가입 요청의 상태를 승인으로 변경합니다.")
	@PutMapping("/{request-id}/accept")
	@NotGuest
	public ResponseEntity<Void> acceptCrewJoinRequest(
		@PathVariable("crew-id") Long crewId,
		@PathVariable("request-id") Long crewJoinRequestId,
		@Auth final Accessor accessor
	) {

		crewFacade.updateCrewJoinRequest(accessor.getMemberId(), crewId, crewJoinRequestId,
			CrewJoinRequestStatus.ACCEPT);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루 가입 요청 거절", description = "크루 가입 요청의 상태를 거절로 변경합니다.")
	@PutMapping("/{request-id}/deny")
	@NotGuest
	public ResponseEntity<Void> denyCrewJoinRequest(
		@PathVariable("crew-id") Long crewId,
		@PathVariable("request-id") Long crewJoinRequestId,
		@Auth final Accessor accessor
	) {

		crewFacade.updateCrewJoinRequest(accessor.getMemberId(), crewId, crewJoinRequestId, CrewJoinRequestStatus.DENY);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루 가입 요청 삭제", description = "크루 가입 요청을 취소합니다.")
	@DeleteMapping
	@NotGuest
	public ResponseEntity<Void> deleteCrewJoinRequests(
		@PathVariable("crew-id") Long crewId,
		@Auth final Accessor accessor) {

		crewFacade.deleteCrewJoinRequest(accessor.getMemberId(), crewId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "크루 가입 요청 목록 조회", description = "크루의 가입 요청 목록을 조회합니다.")
	@GetMapping
	@NotGuest
	public ResponseEntity<Map<String, Object>> selectCrewJoinRequests(
		@PathVariable("crew-id") Long crewId,
		@ModelAttribute CrewJoinRequestSearchFilter filter,
		@Auth final Accessor accessor
	) {

		if (filter == null) {
			throw new IllegalArgumentException("조회 옵션은 필수입니다.");
		}

		List<CrewJoinRequestListResponse> crewJoinRequestList = crewFacade
			.selectCrewJoinRequestsByStatus(accessor.getMemberId(), crewId, filter);
		int totalCount = crewFacade.selectTotalCount(crewId, filter.getStatus().name(), filter.getNickname());
		Map<String, Object> result = new HashMap<>();
		result.put("crewJoinRequestList", crewJoinRequestList);
		result.put("totalCount", totalCount);

		return ResponseEntity.ok(result);
	}

}