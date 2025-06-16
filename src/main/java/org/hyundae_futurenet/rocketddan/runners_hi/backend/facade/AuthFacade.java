package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.LoginRequest;

public interface AuthFacade {

	String reissueAccessToken(String refreshToken);

	Member login(LoginRequest loginRequest);
}
