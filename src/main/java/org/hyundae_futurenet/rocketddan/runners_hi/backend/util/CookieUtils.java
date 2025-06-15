package org.hyundae_futurenet.rocketddan.runners_hi.backend.util;

import static lombok.AccessLevel.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@Component
public class CookieUtils {

	public static ResponseCookie buildCookie(
		String name,
		String value,
		long maxAgeMinutes,
		String path) {

		return ResponseCookie.from(name, value)
			.maxAge(Duration.ofMinutes(maxAgeMinutes))
			.path(path)
			.httpOnly(true)
			.secure(true)
			.sameSite(SameSite.NONE.attributeValue())
			.build();
	}

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name,
		String path) {

		Optional<Cookie> cookieOptional = getCookie(request, name);
		if (cookieOptional.isPresent()) {
			ResponseCookie deleteCookie = buildCookie(
				name,
				"",
				0,
				path);
			response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
		}
	}

	public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {

		Cookie[] cookies = request.getCookies();
		if (Objects.isNull(cookies)) {
			return Optional.empty();
		}
		return Arrays.stream(cookies)
			.filter(cookie -> cookie.getName().equals(name))
			.findFirst();
	}
}
