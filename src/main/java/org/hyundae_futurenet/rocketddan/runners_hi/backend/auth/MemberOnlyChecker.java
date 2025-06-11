package org.hyundae_futurenet.rocketddan.runners_hi.backend.auth;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.exception.auth.InvalidAuthorityException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth.Accessor;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MemberOnlyChecker {

	@Before("@annotation(org.hyundae_futurenet.rocketddan.runners_hi.backend.auth.MemberOnly)")
	public void check(final JoinPoint joinPoint) {

		Arrays.stream(joinPoint.getArgs())
			.filter(Accessor.class::isInstance)
			.map(Accessor.class::cast)
			.filter(Accessor::isMember)
			.findFirst()
			.orElseThrow(InvalidAuthorityException::new);
	}
}