package org.hyundae_futurenet.rocketddan.runners_hi.backend.config.auth;

import static org.hyundae_futurenet.rocketddan.runners_hi.backend.util.auth.JwtTokenProvider.*;

import java.io.IOException;
import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.CustomException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant.Role;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.CookieUtils;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.auth.JwtTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain)
		throws ServletException, IOException {

		Accessor accessor = Accessor.guest();

		Optional<Cookie> accessTokenOptional = CookieUtils.getCookie(request, ACCESS_TOKEN_COOKIE_NAME);
		if (accessTokenOptional.isPresent()) {
			long memberId = 0L;
			Role role = Role.USER;
			try {
				String accessToken = accessTokenOptional.get().getValue();
				memberId = jwtTokenProvider.getMemberId(accessToken);
				role = jwtTokenProvider.getRole(accessToken);
			} catch (CustomException e) {
				ErrorCode errorCode = e.getErrorCode();
				response.setStatus(errorCode.getStatus().value());
				response.setContentType("text/plain; charset=UTF-8");
				response.getWriter().write("⚠️ " + errorCode.getMessage());
			}

			switch (role) {
				case USER -> accessor = Accessor.user(memberId);
				case ADMIN -> accessor = Accessor.admin(memberId);
				case COMPANY -> accessor = Accessor.company(memberId);
			}
		}

		log.info("Request URI: {}, Method: {}, memberId: {}, Authority: {}", request.getRequestURI(),
			request.getMethod(), accessor.isGuest() ? "비로그인 사용자" : accessor.getMemberId(), accessor.getAuthority());
		filterChain.doFilter(request, response);
	}
}
