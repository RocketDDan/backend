package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.Objects;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.SignUpRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.member.MemberService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file.S3FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2FacadeImpl implements OAuth2Facade {

	private final MemberService memberService;

	private final S3FileUtil s3FileUtil;

	@Transactional
	@Override
	public void signUp(SignUpRequest signUpRequest, MultipartFile profileImage) {

		long memberId = memberService.signUp(signUpRequest);

		if (Objects.nonNull(profileImage) && !profileImage.isEmpty()) {
			String uploadedFilePath = s3FileUtil.uploadMemberProfile(profileImage, memberId);
			memberService.updateProfileImage(memberId, uploadedFilePath);
		}
	}
}
