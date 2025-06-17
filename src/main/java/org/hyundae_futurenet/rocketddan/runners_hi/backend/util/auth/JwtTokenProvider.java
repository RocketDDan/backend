package org.hyundae_futurenet.rocketddan.runners_hi.backend.util.auth;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.config.JwtProperties;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.ErrorCode;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth.InvalidTokenException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth.TokenExpiredException;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant.Role;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant.TokenType;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";

	public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

	public static final String SIGNUP_TOKEN_COOKIE_NAME = "signup_token";

	private final JwtProperties jwtProperties;

	private SecretKey key;

	@PostConstruct
	public void init() {

		byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateAccessToken(long memberId, String memberEmail, Role role) {

		Map<String, Object> claims = Map.of(
			"type", TokenType.ACCESS.name(),
			"memberId", String.valueOf(memberId),
			"memberEmail", memberEmail,
			"role", role.name()
		);
		return generateToken(claims, jwtProperties.getAccessTokenExpirationMinutes());
	}

	public String generateRefreshToken(long memberId, String memberEmail, Role role) {

		Map<String, Object> claims = Map.of(
			"type", TokenType.REFRESH.name(),
			"memberId", String.valueOf(memberId),
			"memberEmail", memberEmail,
			"role", role.name()
		);
		return generateToken(claims, jwtProperties.getRefreshTokenExpirationMinutes());
	}

	public String generateSignupToken(String email, String provider) {

		Map<String, Object> claims = Map.of(
			"type", TokenType.SIGNUP.name(),
			"email", email,
			"provider", provider
		);
		return generateToken(claims, jwtProperties.getSignupTokenExpirationMinutes());
	}

	private String generateToken(Map<String, Object> claims, long expirationMinutes) {

		final Date now = new Date();
		final Date expiration = new Date(now.getTime() + Duration.ofMinutes(expirationMinutes).toMillis());

		return Jwts.builder()
			.issuer(jwtProperties.getIssuer())
			.issuedAt(now)
			.expiration(expiration)
			.claims(claims)
			.signWith(key)
			.compact();
	}

	public long getMemberId(String token) {

		return Long.parseLong(
			getPayload(token).get("memberId", String.class)
		);
	}

	public String getMemberEmail(String token) {

		return getPayload(token).get("memberEmail", String.class);
	}

	public Role getRole(String token) {

		return Role.valueOf(
			getPayload(token).get("role", String.class)
		);
	}

	public String getEmail(String token) {

		return getPayload(token).get("email", String.class);
	}

	private Claims getPayload(String token) {

		try {
			JwtParser jwtParser = Jwts.parser()
				.verifyWith(key)
				.build();
			return jwtParser.parseSignedClaims(token)
				.getPayload();
		} catch (ExpiredJwtException e) {
			TokenType type = TokenType.valueOf(e.getClaims().get("type", String.class));
			switch (type) {
				case ACCESS -> throw new TokenExpiredException(ErrorCode.ACCESS_TOKEN_EXPIRED);
				case REFRESH -> throw new TokenExpiredException(ErrorCode.REFRESH_TOKEN_EXPIRED);
				case SIGNUP -> throw new TokenExpiredException(ErrorCode.SIGNUP_TOKEN_EXPIRED);
				default -> throw new TokenExpiredException(ErrorCode.TOKEN_EXPIRED);
			}
		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidTokenException();
		}
	}
}
