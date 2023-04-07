package com.b101.recruit.config;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import lombok.Data;
import lombok.ToString;

@Data
@Configuration
@ConstructorBinding
@ConfigurationProperties("spring.mail")
@ToString
public class MailConfig {
	
	private static final String MAIL_DEBUG = "mail.debug";
	private static final String MAIL_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
	private static final String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	
	private String host;
	private String protocol;
	private int port;
	private String username;
	private String password;
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setProtocol(getProtocol());
		mailSender.setHost(getHost());
		mailSender.setPort(getPort());
		mailSender.setUsername(getUsername());
		mailSender.setPassword(getPassword());
		Properties properties = mailSender.getJavaMailProperties();
		properties.put(MAIL_SMTP_SSL_ENABLE, true);
		properties.put(MAIL_SMTP_STARTTLS_REQUIRED, true);
		properties.put(MAIL_SMTP_STARTTLS_ENABLE, true);
		properties.put(MAIL_SMTP_AUTH, true);
		properties.put(MAIL_DEBUG, true);
		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}
}
