package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.Objects;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.NotGuest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.CrewFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.MemberFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CrewJoinRequestSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MemberProfileResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.MemberResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.NicknameCheckResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.crew.CrewDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	private final CrewFacade crewFacade;

	@Operation(summary = "로그인한 회원 정보 조회", description = "로그인한 회원의 정보를 조회한다.")
	@NotGuest
	@GetMapping("/personal-info")
	public ResponseEntity<MemberResponse> findMember(@Auth final Accessor accessor) {

		final MemberResponse memberResponse = memberFacade.findMember(accessor.getMemberId());
		return ResponseEntity.ok(memberResponse);
	}

	@Operation(summary = "특정 회원 프로필 조회", description = "특정 회원의 프로필 정보를 조회한다.")
	@GetMapping("/{memberId}/profile")
	public ResponseEntity<MemberProfileResponse> findMemberProfileByMemberId(
		@Auth final Accessor accessor,
		@PathVariable("memberId") final Long memberId
	) {

		final MemberResponse memberResponse = memberFacade.findMember(memberId);
		Long crewId = crewFacade.selectMyCrew(memberId);
		CrewJoinRequestSource requestJoinCrew = crewFacade.selectCrewJoinRequestByMemberId(accessor.getMemberId(),
			memberId);
		Long requestJoinCrewId = null;
		String requestJoinCrewName = null;
		if (requestJoinCrew != null) {
			requestJoinCrewId = requestJoinCrew.crewId();
			requestJoinCrewName = requestJoinCrew.crewName();
		}
		CrewDetailResponse crewDetailResponse = null;
		if (Objects.nonNull(crewId)) {
			crewDetailResponse = crewFacade.selectCrewByCrewId(memberId, crewId);
		}
		MemberProfileResponse memberProfileResponse = MemberProfileResponse.builder()
			.email(memberResponse.getEmail())
			.nickname(memberResponse.getNickname())
			.profileImageUrl(memberResponse.getProfileImageUrl())
			.crewName(Objects.nonNull(crewId) ? crewDetailResponse.getCrewName() : null)
			.isLeader(Objects.nonNull(crewId) && crewDetailResponse.isLeader())
			.crewId(crewId)
			.requestJoinCrewId(requestJoinCrewId)
			.requestJoinCrewName(requestJoinCrewName)
			.build();
		return ResponseEntity.ok(memberProfileResponse);
	}

	@Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 여부를 확인한다.")
	@GetMapping("/nickname-check")
	public ResponseEntity<NicknameCheckResponse> checkNicknameDuplicate(
		@RequestParam("nickname") final String nickname
	) {

		NicknameCheckResponse nicknameCheckResponse = memberFacade.existsByNickname(nickname);
		return ResponseEntity.ok(nicknameCheckResponse);
	}
}
