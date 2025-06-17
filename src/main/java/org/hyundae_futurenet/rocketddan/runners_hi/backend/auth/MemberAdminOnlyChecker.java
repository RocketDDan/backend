package org.hyundae_futurenet.rocketddan.runners_hi.backend.auth;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth.InvalidAuthorityException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MemberAdminOnlyChecker {

	@Before("@annotation(org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.MemberAdminOnly)")
	public void check(final JoinPoint joinPoint) {

		Arrays.stream(joinPoint.getArgs())
			.filter(Accessor.class::isInstance)
			.map(Accessor.class::cast)
			.filter(accessor -> accessor.isMember() || accessor.isAdmin())
			.findFirst()
			.orElseThrow(InvalidAuthorityException::new);
	}
}