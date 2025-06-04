package org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class S3FileUtilTest {

	@Autowired
	private S3FileUtil s3FileUtil;

	@Test
	void uploadCrewProfile_shouldActuallyUploadToS3() throws Exception {
		// given
		MockMultipartFile mockFile = new MockMultipartFile(
			"file",
			"integration-test.png",
			"image/png",
			"fake-integration-data".getBytes()
		);

		long testCrewId = 9999L;

		// when
		String path = s3FileUtil.uploadCrewProfile(mockFile, testCrewId);

		// then
		assertNotNull(path, "S3 업로드 후 반환된 경로가 null입니다.");
		assertTrue(path.contains("crew-profile/" + testCrewId + "/"),
			"경로에 crew-profile/" + testCrewId + " 포함되어야 함");

		System.out.println("✅ 실제 S3에 업로드된 파일 경로: " + path);
	}
}