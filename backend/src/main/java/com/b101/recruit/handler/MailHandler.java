package com.b101.recruit.handler;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.b101.recruit.domain.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class MailHandler {
	
	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;
	
	public void sendAuthEmail(String email, String emailToken) {
//		body(메일 내용) 작성
		StringBuilder body = new StringBuilder();
		body.append(getAuthMailBody(emailToken));
		MimeMessage mm = mailSender.createMimeMessage();
		MimeMessageHelper mmh;
		try {
			mmh = new MimeMessageHelper(mm, true, "UTF-8");
			mmh.setFrom(new InternetAddress(from,"취준진담","UTF-8"));
			mmh.setTo(email);
			mmh.setSubject("[ 취준진담 ] 이메일 인증 안내 "); // 메일 제목
			mmh.setText(body.toString(), true); // 메일 내용
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		mailSender.send(mm);
	}
	
	public String getAuthMailBody(String emailToken) {

		StringBuilder sb = new StringBuilder();

		sb.append(
				"<div style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">");
		sb.append("<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">");
		sb.append("<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">JAM</span><br />");
		sb.append("<span style=\"color: {$point_color};\">메일인증</span> 안내입니다.");
		sb.append("</h1>");
		sb.append("<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">");
		sb.append("안녕하세요.<br />");
		sb.append("취준진담에 가입해 주셔서 진심으로 감사드립니다.<br />");
		sb.append("아래 인증번호를 입력하시고 회원가입을 완료해 주세요.<br />");
		sb.append("감사합니다.</p>");
		sb.append(
				"<p style=\"color: #FFF; text-decoration: none; text-align: center;\" href=\"#\" target=\"_blank\"><p style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">"
						+ emailToken + "</p></p>");
		sb.append("<div style=\"border-top: 1px solid #DDD; padding: 5px;\">");
		sb.append("<p style=\"font-size: 13px; line-height: 21px; color: #555;\">");
		sb.append("JAM url.<br />");
		sb.append("</p></div></div>");

		return sb.toString();

	}
	
	public void sendPasswordEmail(User user, String tempPassword) {
//		body(메일 내용) 작성
		StringBuilder body = new StringBuilder();
		body.append(getPasswordMailBody(user.getName(), tempPassword));
		MimeMessage mm = mailSender.createMimeMessage();
		MimeMessageHelper mmh;

		try {
			mmh = new MimeMessageHelper(mm, true, "UTF-8");
			mmh.setFrom(new InternetAddress(from,"취준진담","UTF-8"));
			mmh.setTo(user.getEmail());
			mmh.setSubject("[ 취준진담 ] 임시 비밀번호 안내 "); // 메일 제목
			mmh.setText(body.toString(), true); // 메일 내용
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		mailSender.send(mm);
	}
	
	public String getPasswordMailBody(String userName, String tempPassword) {
		StringBuilder sb = new StringBuilder();

		sb.append(
				"<div style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">");
		sb.append("<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">");
		sb.append("<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">JAM</span><br />");
		sb.append("<span style=\"color: {$point_color};\">메일인증</span> 안내입니다.");
		sb.append("</h1>");
		sb.append("<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">");
		sb.append("안녕하세요. " + userName + "님<br/>");
		sb.append("고객님의 임시 비밀번호를 알려드립니다.<br />");
		sb.append("임시 비밀번호로 로그인하신 후 원하시는 비밀번호로 수정해서 이용하시기 바랍니다.<br />");
		sb.append("감사합니다.</p>");
		sb.append(
				"<p style=\"color: #FFF; text-decoration: none; text-align: center;\" href=\"#\" target=\"_blank\"><p style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">"
						+ tempPassword + "</p></p>");
		sb.append("<div style=\"border-top: 1px solid #DDD; padding: 5px;\">");
		sb.append("<p style=\"font-size: 13px; line-height: 21px; color: #555;\">");
		sb.append("※ 참고하세요!<br/>");
		sb.append("임시 비밀번호로 로그인 하신 후, 반드시 비밀번호를 수정해 주세요.<br/>");
		sb.append("비밀번호는 취준진담 로그인 &gt; 마이페이지 &gt;회원정보수정 에서 수정하실 수 있습니다.<br/>");
		sb.append("안전한 서비스 이용을 위해서 비밀번호는 정기적으로 변경해주는 것이 좋습니다.<br/>");
		sb.append("</p></div></div>");

		return sb.toString();
	}

}
