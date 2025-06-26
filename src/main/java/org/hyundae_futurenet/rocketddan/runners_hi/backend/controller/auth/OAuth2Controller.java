package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller.auth;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth.InvalidTokenException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.OAuth2Facade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.SignUpRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.auth.JwtTokenProvider;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "OAuth2 API", description = "OAuth2 API")
@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

	private final OAuth2Facade oAuth2Facade;

	private final JwtTokenProvider jwtTokenProvider;

	@Operation(summary = "OAuth2 회원가입", description = "OAuth2 회원가입을 처리합니다.")
	@PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> signUp(
		@CookieValue(value = JwtTokenProvider.SIGNUP_TOKEN_COOKIE_NAME) String signupToken,
		@Valid @RequestPart("signUp") SignUpRequest signUpRequest,
		@RequestPart(value = "profileImage", required = false) MultipartFile profileImage
	) {

		String tokenEmail = jwtTokenProvider.getEmail(signupToken);
		if (!tokenEmail.equals(signUpRequest.getEmail())) {
			throw new InvalidTokenException();
		}
		oAuth2Facade.signUp(signUpRequest, profileImage);
		return ResponseEntity.ok().build();
	}
}
