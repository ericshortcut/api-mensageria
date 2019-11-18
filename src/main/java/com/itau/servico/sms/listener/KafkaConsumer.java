package com.itau.servico.sms.listener;

import com.itau.servico.sms.model.SMSMessage;
import com.itau.servico.sms.service.TwilioSMS;
import com.twilio.exception.TwilioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @Autowired
    TwilioSMS twilioSendSMS;

    @KafkaListener( topics = "${app.topics.sms-consumer}", containerFactory = "kafkaSMSMessageListenerContainerFactory")
    public void consume(SMSMessage smsMessage){
        try {
            twilioSendSMS.send(smsMessage);
        } catch (TwilioException e){
            log.error(e.getMessage());
            // TODO: Implementar fallback para mensagens que falharem
        }
    }
}
