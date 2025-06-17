package org.hyundae_futurenet.rocketddan.runners_hi.backend.util.mail;

import java.io.File;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleMailUtil {

	private final JavaMailSender mailSender;

	@Async
	/// 크루장에게 보내는 크루 가입 요청 메일
	public void sendCrewJoinRequestMail(String to) {

		String subject = "새로운 크루 가입 요청이 도착했습니다!";
		String html = """
			<div style="font-family: Arial, sans-serif; color: #333;">
			    <h2>안녕하세요, 크루장님!</h2>
			    <p>새로운 멤버가 크루 가입을 신청했습니다.</p>
			    <p>가입 요청을 확인하고 승인 여부를 결정해 주세요.</p>
			    <br/>
			    <a href="https://www.runners-hi.shop" 
			       style="display: inline-block; padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px;">
			       가입 요청 확인하기
			    </a>
			    <br/><br/>
			    <p>감사합니다.<br/>Runners Hi 드림</p>
			</div>
			""";

		try {
			sendHtmlMail(to, subject, html);
		} catch (MessagingException e) {
			log.info(e.getMessage());
		}
	}

	@Async
	/// 크루원이 된 멤버에게 보내는 크루 가입 완료 메일
	public void sendCrewJoinSuccessMail(String to) {

		String subject = "크루 가입이 완료되었습니다!";
		String html = """
			<div style="font-family: Arial, sans-serif; color: #333;">
			    <h2>환영합니다!</h2>
			    <p>회원님의 크루 가입이 성공적으로 완료되었습니다.</p>
			    <p>이제부터 다양한 활동에 참여하실 수 있습니다. 즐거운 크루 활동 되시길 바랍니다!</p>
			    <br/>
			    <a href="https://www.runners-hi.shop" 
			       style="display: inline-block; padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px;">
			       서비스 바로가기
			    </a>
			    <br/><br/>
			    <p>감사합니다.<br/>Runners Hi 드림</p>
			</div>
			""";

		try {
			sendHtmlMail(to, subject, html);
		} catch (MessagingException e) {
			log.info(e.getMessage());
		}
	}

	/**
	 * HTML 메일 + 첨부파일
	 */
	private void sendHtmlMail(String to, String subject, String htmlContent, File file) throws
		MessagingException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlContent, true);
		helper.setFrom("your_email@gmail.com");

		helper.addAttachment(file.getName(), file); // 첨부파일 추가

		mailSender.send(message);
	}

	/**
	 * HTML 메일 + 첨부파일
	 */
	private void sendHtmlMail(String to, String subject, String htmlContent) throws
		MessagingException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlContent, true);
		helper.setFrom("your_email@gmail.com");

		mailSender.send(message);
	}

	private void sendSimpleMail(String to, String subject, String text) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		message.setFrom("Runners Hi <guseo001237@gmail.com>");

		mailSender.send(message);
	}
}