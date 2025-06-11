package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import static org.hyundae_futurenet.rocketddan.runners_hi.backend.util.CookieUtils.*;
import static org.hyundae_futurenet.rocketddan.runners_hi.backend.util.auth.JwtTokenProvider.*;
import static org.springframework.http.HttpStatus.*;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.NotGuest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.config.AppProperties;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.config.JwtProperties;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.facade.AuthFacade;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.CookieUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth API", description = "Auth API")
@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

	private final AuthFacade authFacade;

	private final JwtProperties jwtProperties;

	private final AppProperties appProperties;

	@Operation(summary = "로그아웃", description = "로그아웃을 수행한다. 쿠키에 저장된 엑세스토큰과 리프레시토큰을 삭제한다.")
	@NotGuest
	@DeleteMapping("/logout")
	public ResponseEntity<Void> logout(
		@Auth final Accessor accessor,
		HttpServletRequest request,
		HttpServletResponse response) {

		CookieUtils.deleteCookie(request, response, ACCESS_TOKEN_COOKIE_NAME);
		CookieUtils.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "엑세스토큰 재발급", description = "리프레시토큰으로 만료한 엑세스토큰을 재발급한다.")
	@PostMapping("/token/reissue")
	public ResponseEntity<Void> extendLogin(
		@CookieValue(REFRESH_TOKEN_COOKIE_NAME) final String refreshToken,
		HttpServletRequest request,
		HttpServletResponse response
	) {

		String newAccessToken = authFacade.reissueAccessToken(refreshToken);
		ResponseCookie accessCookie = buildCookie(
			ACCESS_TOKEN_COOKIE_NAME,
			newAccessToken,
			jwtProperties.getAccessTokenExpirationMinutes(),
			"/");
		CookieUtils.deleteCookie(request, response, ACCESS_TOKEN_COOKIE_NAME);
		response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

		return ResponseEntity
			.status(CREATED)
			.build();
	}
}
