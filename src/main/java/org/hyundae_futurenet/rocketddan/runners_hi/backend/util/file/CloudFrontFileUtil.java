package org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file;

import java.io.File;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.config.CloudFrontProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CloudFrontFileUtil {

	private final CloudFrontProperties properties;

	/// 지정된 경로로 Signed URL 생성
	public String generateSignedUrl(String objectKey, long validSeconds) {

		Date expirationDate = new Date(System.currentTimeMillis() + validSeconds * 1000);

		try {
			return CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
				SignerUtils.Protocol.https,
				properties.getDistributionDomain(),
				new File(properties.getPrivateKeyPath()),
				objectKey,
				properties.getKeyPairId(),
				expirationDate
			);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}