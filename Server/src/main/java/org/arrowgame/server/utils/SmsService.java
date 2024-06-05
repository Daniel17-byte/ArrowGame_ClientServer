package org.arrowgame.server.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService implements SenderService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void send(String to, String body) {
        Message message = Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(fromNumber),
                        body)
                .create();
    }
}