package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import java.util.List;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.AdminFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.AdminMemberResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminFacade adminFacade;

	@GetMapping("/members")
	public ResponseEntity<List<AdminMemberResponse>> getAdminMembers() {

		List<AdminMemberResponse> members = adminFacade.getAdminMembers();
		return new ResponseEntity<>(members, HttpStatus.OK);
	}
}
