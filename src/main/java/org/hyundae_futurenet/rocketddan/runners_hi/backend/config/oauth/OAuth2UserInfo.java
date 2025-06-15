package org.hyundae_futurenet.rocketddan.runners_hi.backend.config.oauth;

public interface OAuth2UserInfo {

	String getProvider();

	String getProviderId();

	String getNickname();

	String getProfileImageUrl();

	String getEmail();
}
