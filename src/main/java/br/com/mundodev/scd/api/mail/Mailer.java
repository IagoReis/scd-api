package br.com.mundodev.scd.api.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Mailer {
	
	@Value("${app.mail.no-reply-email-adress}")
	private String noReplyEmailAddress;
	
	private JavaMailSender mailSender;
	
	@Autowired
	public Mailer(final JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Async
	public void enviar(final String email, final String subject, final String text) {
		
		final SimpleMailMessage mailMessage = new SimpleMailMessage();
				
		mailMessage.setFrom(noReplyEmailAddress);
		mailMessage.setTo(email);		
		mailMessage.setSubject(subject);
		mailMessage.setText(text);
		
		//mailSender.send(mailMessage);		
	}
	
}
