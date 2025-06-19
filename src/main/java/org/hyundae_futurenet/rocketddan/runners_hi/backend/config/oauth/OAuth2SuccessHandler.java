package org.hyundae_futurenet.rocketddan.runners_hi.backend.config.oauth;

import static org.hyundae_futurenet.rocketddan.runners_hi.backend.util.CookieUtils.*;
import static org.hyundae_futurenet.rocketddan.runners_hi.backend.util.auth.JwtTokenProvider.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.config.AppProperties;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.config.JwtProperties;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.member.MemberException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.LoginResult;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.member.MemberMapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.CookieUtils;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.auth.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	private final JwtProperties jwtProperties;

	private final AppProperties appProperties;

	private final JwtTokenProvider jwtTokenProvider;

	private final MemberMapper memberMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		String email = oAuth2User.getAttribute("email");
		String nickname = oAuth2User.getAttribute("nickname");
		String provider = oAuth2User.getAttribute("provider");

		Optional<Member> optionalMember = memberMapper.findByEmail(email);
		if (optionalMember.isPresent()) {
			Member member = optionalMember.get();
			LoginResult loginResult = processExistingMember(member, jwtTokenProvider, jwtProperties, appProperties);
			CookieUtils.deleteCookie(request, response, ACCESS_TOKEN_COOKIE_NAME, "/");
			response.addHeader(HttpHeaders.SET_COOKIE, loginResult.getAccessTokenCookie());
			CookieUtils.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME, "/auth");
			response.addHeader(HttpHeaders.SET_COOKIE, loginResult.getRefreshTokenCookie());
			response.setStatus(HttpStatus.OK.value());
			response.sendRedirect(loginResult.getRedirectUrl());
		} else {
			processNewMember(request, response, email, nickname, provider);
		}
	}

	private void processNewMember(HttpServletRequest request,
		HttpServletResponse response,
		String email,
		String nickname,
		String provider) throws IOException {

		String signupToken = jwtTokenProvider.generateSignupToken(email, provider);
		ResponseCookie signupCookie = buildCookie(
			SIGNUP_TOKEN_COOKIE_NAME,
			signupToken,
			jwtProperties.getSignupTokenExpirationMinutes(),
			"/oauth2/signup");
		CookieUtils.deleteCookie(request, response, SIGNUP_TOKEN_COOKIE_NAME, "/oauth2/signup");
		response.addHeader(HttpHeaders.SET_COOKIE, signupCookie.toString());

		String targetUrl = UriComponentsBuilder.fromUriString(appProperties.getClientDomain())
			.path("/signup")
			.queryParam("email", email)
			.queryParam("nickname", URLEncoder.encode(nickname, StandardCharsets.UTF_8))
			.build()
			.toUriString();

		response.sendRedirect(targetUrl);
	}

	public static LoginResult processExistingMember(
		Member member,
		JwtTokenProvider jwtTokenProvider,
		JwtProperties jwtProperties,
		AppProperties appProperties) {

		if (Objects.nonNull(member.getDeletedAt())) {
			throw new MemberException(ErrorCode.MEMBER_DELETED);
		}

		String accessToken = jwtTokenProvider.generateAccessToken(member.getMemberId(), member.getEmail(),
			member.getRole());
		ResponseCookie accessTokenCookie = buildCookie(
			ACCESS_TOKEN_COOKIE_NAME,
			accessToken,
			(jwtProperties.getAccessTokenExpirationMinutes() * 24),
			"/");

		String refreshToken = jwtTokenProvider.generateRefreshToken(member.getMemberId(), member.getEmail(),
			member.getRole());
		ResponseCookie refreshTokenCookie = buildCookie(
			REFRESH_TOKEN_COOKIE_NAME,
			refreshToken,
			jwtProperties.getRefreshTokenExpirationMinutes(),
			"/auth");

		String redirectUrl = UriComponentsBuilder.fromUriString(appProperties.getClientDomain())
			.path("/auth/callback")
			.build()
			.toUriString();

		return new LoginResult(
			accessTokenCookie.toString(),
			refreshTokenCookie.toString(),
			redirectUrl
		);
	}
}
