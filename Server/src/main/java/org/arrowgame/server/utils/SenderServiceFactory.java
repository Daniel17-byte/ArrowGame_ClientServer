package org.arrowgame.server.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SenderServiceFactory {
    private final SmsService smsService;
    private final EmailService emailService;

    @Autowired
    public SenderServiceFactory(SmsService smsService, EmailService emailService) {
        this.smsService = smsService;
        this.emailService = emailService;
    }

    public SenderService getSenderService(String type) {
        if ("SMS".equalsIgnoreCase(type)) {
            return smsService;
        } else if ("EMAIL".equalsIgnoreCase(type)) {
            return emailService;
        }
        throw new IllegalArgumentException("Unknown sender service type: " + type);
    }
}