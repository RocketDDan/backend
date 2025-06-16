package org.hyundae_futurenet.rocketddan.runners_hi.backend.util.mail;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.config.AwsSesConfig;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.MailSendRequest;
import org.springframework.stereotype.Component;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SESMailUtil {

	private final AwsSesConfig awsSesConfig;

	public void send(MailSendRequest mailSendRequest) {

		try {
			AmazonSimpleEmailService client = awsSesConfig.amazonSimpleEmailService();
			client.sendEmail(mailSendRequest.toSendRequestDto());
		} catch (Exception ex) {
			log.info("이메일 전송 중 에러: {}", ex.getMessage());
			throw new IllegalArgumentException("이메일 전송 서비스가 원활하지 않습니다.");
		}
	}
}