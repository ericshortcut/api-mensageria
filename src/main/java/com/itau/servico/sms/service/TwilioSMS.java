package com.itau.servico.sms.service;

import com.itau.servico.sms.model.SMSMessage;
import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@Slf4j
public class TwilioSMS {


    @Value("${twilio.ACCOUNT_SID}")
    private String ACCOUNT_SID;

    @Value("${twilio.AUTH_TOKEN}")
    private String AUTH_TOKEN;

    @Value("${twilio.TWILIO_PHONE}")
    private String TWILIO_PHONE;

    public void send(SMSMessage smsMessage){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        try {
            Message message = Message.creator(
                    new PhoneNumber(smsMessage.getTo()),
                    new PhoneNumber(TWILIO_PHONE),
                    smsMessage.getBody())
                    .create();
        } catch (TwilioException e ){
            log.error(e.getMessage());

        }
    }
}
