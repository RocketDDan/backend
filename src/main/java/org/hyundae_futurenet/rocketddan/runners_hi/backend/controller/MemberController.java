package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.NotGuest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.MemberFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MemberInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Member API", description = "Member API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberFacade memberFacade;

	@Operation(summary = "회원 정보 조회", description = "로그인한 회원의 정보를 조회한다.")
	@NotGuest
	@GetMapping("/personal-info")
	public ResponseEntity<MemberInfoResponse> getPersonalInfo(@Auth final Accessor accessor) {

		final MemberInfoResponse memberInfoResponse = memberFacade.getPersonalInfo(accessor.getMemberId());
		return ResponseEntity.ok(memberInfoResponse);
	}
}
