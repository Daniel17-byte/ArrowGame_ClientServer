package org.arrowgame.server.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements SenderService {
    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void send(String to, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("new data");
        message.setText(body);
        emailSender.send(message);
    }
}
