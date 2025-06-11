package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.SignUpRequest;
import org.springframework.web.multipart.MultipartFile;

public interface OAuth2Facade {

	void signUp(SignUpRequest signUpRequest, MultipartFile profileImage);
}
