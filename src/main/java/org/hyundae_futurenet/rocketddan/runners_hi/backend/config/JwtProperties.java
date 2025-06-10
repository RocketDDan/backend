package org.hyundae_futurenet.rocketddan.runners_hi.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {

	private String issuer;

	private String secretKey;

	private Integer refreshTokenExpirationMinutes;

	private Integer accessTokenExpirationMinutes;

	private Integer signupTokenExpirationMinutes;
}
