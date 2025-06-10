package org.hyundae_futurenet.rocketddan.runners_hi.backend.config.oauth;

import java.util.Map;
import java.util.Objects;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

	private final Map<String, Object> attributes;

	private final Map<String, Object> kakaoAccountAttributes;

	private final Map<String, Object> profileAttributes;

	public KakaoOAuth2UserInfo(Map<String, Object> attributes) {

		this.attributes = attributes;
		this.kakaoAccountAttributes = (Map<String, Object>) attributes.get("kakao_account");
		this.profileAttributes = (Map<String, Object>) kakaoAccountAttributes.get("profile");
	}

	@Override
	public String getProvider() {

		return "kakao";
	}

	@Override
	public String getProviderId() {

		return attributes.get("id").toString();
	}

	@Override
	public String getNickname() {

		if (Objects.isNull(profileAttributes)) {
			return null;
		}
		return (String) profileAttributes.get("nickname");
	}

	@Override
	public String getProfileImageUrl() {

		if (Objects.isNull(profileAttributes)) {
			return null;
		}
		return (String) profileAttributes.get("profile_image_url");
	}

	@Override
	public String getEmail() {

		if (Objects.isNull(kakaoAccountAttributes) || !kakaoAccountAttributes.containsKey("email")) {
			throw new OAuth2AuthenticationException("OAuth2 provider에서 이메일 정보를 찾을 수 없습니다.");
		}
		return (String) kakaoAccountAttributes.get("email");
	}
}
