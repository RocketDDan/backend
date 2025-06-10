package org.hyundae_futurenet.rocketddan.runners_hi.backend.config.oauth;

import java.util.Optional;

public interface OAuth2UserInfo {

	String getProvider();

	String getProviderId();

	String getNickname();

	String getProfileImageUrl();

	String getEmail();
}
