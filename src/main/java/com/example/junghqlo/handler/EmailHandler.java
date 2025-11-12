package com.example.junghqlo.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailHandler {

  // 0. constructor injection ----------------------------------------------------------------------
  JavaMailSender mailSender;
  Map<String, String> emailCodeMap = new HashMap<>();
  EmailHandler(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  // 1. generateCode -------------------------------------------------------------------------------
  public String generateCode() {

    Integer length = 6;
    String chars="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    Random random = new Random();
    StringBuilder builder = new StringBuilder(length);

    for (Integer i = 0; i < length; i++) {
      builder.append(chars.charAt(random.nextInt(chars.length())));
    }
    return builder.toString();
  }

  // 2. sendEmailCode ------------------------------------------------------------------------------
  public String sendEmailCode(String receiveEmail, String emailCode) throws MessagingException {

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

    String subject="JUNGHQLO 이메일 인증번호입니다.";
    String text="인증번호  :  " +  emailCode;

    helper.setTo(receiveEmail);
    helper.setSubject(subject);
    helper.setText(text, false);

    mailSender.send(message);

    // Save email code in the map
    emailCodeMap.put(receiveEmail, emailCode);

    return emailCode;
  }

  // 3. checkEmailCode -----------------------------------------------------------------------------
  public Integer checkEmailCode(String receiveEmail, String inputEmailCode) {

    String storedEmailCode = emailCodeMap.get(receiveEmail);

    if (inputEmailCode.equals(storedEmailCode)) {
      return 1;
    }
    else {
      return 0;
    }
  }

}
