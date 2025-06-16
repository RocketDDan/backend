package org.hyundae_futurenet.rocketddan.runners_hi.backend.config.oauth;

import java.util.Collections;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth.OAuth2AuthException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2UserInfo userInfo = createOAuth2UserInfo(registrationId, oAuth2User);

		return new DefaultOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority("ROLE_GUEST")),
			Map.of(
				"email", userInfo.getEmail(),
				"nickname", userInfo.getNickname(),
				"profileImageUrl", userInfo.getProfileImageUrl(),
				"provider", registrationId
			),
			"email"
		);
	}

	private OAuth2UserInfo createOAuth2UserInfo(String registrationId, OAuth2User oAuth2User) {

		OAuth2UserInfo oAuth2UserInfo = null;
		if (registrationId.equals("kakao")) {
			oAuth2UserInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());
		} else {
			throw new OAuth2AuthException("해당 OAuth 서비스는 제공하지 않습니다.");
		}
		return oAuth2UserInfo;
	}
}
