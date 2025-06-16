package org.hyundae_futurenet.rocketddan.runners_hi.backend.util.mail;

import java.util.ArrayList;
import java.util.Collections;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.MailSendRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SESMailUtilTest {

	@Autowired
	private SESMailUtil SESMailUtil;

	@Test
	public void testSendEmail() {

		// 테스트용 수신자 (본인 이메일 등)
		ArrayList<String> toList = new ArrayList<>(Collections.singletonList("hanol98@naver.com"));

		MailSendRequest request = new MailSendRequest();
		request.setTo(toList);
		request.setSubject("테스트 이메일 제목");
		request.setContent("<h3>이메일 전송 테스트 본문</h3><p>테스트 중입니다.</p>");

		SESMailUtil.send(request);
	}
}