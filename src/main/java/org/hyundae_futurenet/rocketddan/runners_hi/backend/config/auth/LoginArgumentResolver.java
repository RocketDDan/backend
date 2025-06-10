package org.hyundae_futurenet.rocketddan.runners_hi.backend.config.auth;

import static org.hyundae_futurenet.rocketddan.runners_hi.backend.util.auth.JwtTokenProvider.*;

import java.util.Optional;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.Auth;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.exception.BadRequestException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant.Role;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.CookieUtils;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.auth.JwtTokenProvider;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {

		return parameter.hasParameterAnnotation(Auth.class)
			&& parameter.getParameterType().equals(Accessor.class);
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) {

		final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		if (request == null) {
			throw new BadRequestException();
		}

		Optional<Cookie> accessTokenOptional = CookieUtils.getCookie(request, ACCESS_TOKEN_COOKIE_NAME);
		if (accessTokenOptional.isEmpty()) {
			return Accessor.guest();
		}

		String accessToken = accessTokenOptional.get().getValue();
		long memberId = jwtTokenProvider.getMemberId(accessToken);
		Role role = jwtTokenProvider.getRole(accessToken);

		Accessor accessor = Accessor.guest();
		switch (role) {
			case USER -> accessor = Accessor.user(memberId);
			case ADMIN -> accessor = Accessor.admin(memberId);
			case COMPANY -> accessor = Accessor.company(memberId);
		}
		return accessor;
	}
}
