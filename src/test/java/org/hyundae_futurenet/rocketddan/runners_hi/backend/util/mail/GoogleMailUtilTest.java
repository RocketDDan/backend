package org.hyundae_futurenet.rocketddan.runners_hi.backend.util.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GoogleMailUtilTest {

	@Autowired
	private GoogleMailUtil googleMailUtil;

	@Test
	void sendCrewJoinRequestMail() {

		googleMailUtil.sendCrewJoinRequestMail("hanol98@naver.com");
	}

	@Test
	void sendCrewJoinSuccessMail() {

		googleMailUtil.sendCrewJoinSuccessMail("hanol98@naver.com");
	}
}