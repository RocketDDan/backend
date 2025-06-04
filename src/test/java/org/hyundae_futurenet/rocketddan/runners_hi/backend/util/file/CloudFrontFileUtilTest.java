package org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file;

import static org.junit.jupiter.api.Assertions.*;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.config.CloudFrontProperties;
import org.junit.jupiter.api.Test;

class CloudFrontFileUtilTest {

	@Test
	void generateSignedUrl() throws Exception {
		// given
		CloudFrontProperties props = new CloudFrontProperties();
		props.setDistributionDomain("d3j62hm9xnqci3.cloudfront.net");
		props.setKeyPairId("K1CXY699RY3FDX");
		props.setPrivateKeyPath(
			"/Users/jeongjaeyeong/secrets/runners-hi/aws-cloudfront/private_key.pem");

		CloudFrontFileUtil util = new CloudFrontFileUtil(props);

		String objectKey = "test/metamong.jpeg";
		long validSeconds = 60 * 10; // 10분

		// when
		String signedUrl = util.generateSignedUrl(objectKey, validSeconds);

		// then
		System.out.println("Signed URL: " + signedUrl);
		assertNotNull(signedUrl);
		assertTrue(signedUrl.contains("Expires"));
		assertTrue(signedUrl.contains("Signature"));
		assertTrue(signedUrl.contains("Key-Pair-Id"));
	}
}