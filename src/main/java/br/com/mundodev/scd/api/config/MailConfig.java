package br.com.mundodev.scd.api.config;


import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import br.com.mundodev.scd.api.mail.Mailer;

@Configuration
@ComponentScan( basePackageClasses = Mailer.class )
public class MailConfig {
	
	@Value("${app.mail.host}")
	private String host;
	
	@Value("${app.mail.port}")
	private Integer port;
	
	@Value("${app.mail.username}")
	private String username;
	
	@Value("${app.mail.password}")
	private String password;
	
	@Bean
	public JavaMailSender mailSender() {
		
		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		
		final Properties javaMailProperties = new Properties();
		
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.starttls.enable", true);
		javaMailProperties.put("mail.debug", false);
		javaMailProperties.put("mail.smtp.connectiontimeout", 10000);
		
		mailSender.setJavaMailProperties(javaMailProperties);
		
		return mailSender;
	}
	
}
