package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business;

import java.util.ArrayList;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailSendRequest {

	private String from = "러너스 하이 <guseo001237@gmail.com>"; // 보내는사람이름 <이메일주소>

	private ArrayList<String> to;

	private String subject;

	private String content;

	public MailSendRequest(ArrayList<String> to, String subject, String content) {

		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	public void addTo(String email) {

		to.add(email);
	}

	public SendEmailRequest toSendRequestDto() {

		Destination destination = new Destination().withToAddresses(to);
		Message message = new Message()
			.withSubject(createContent(subject))
			.withBody(new Body().withHtml(createContent(content)));
		return new SendEmailRequest()
			.withSource(from)
			.withDestination(destination)
			.withMessage(message);
	}

	private Content createContent(String text) {

		return new Content().withCharset("UTF-8").withData(text);
	}
}