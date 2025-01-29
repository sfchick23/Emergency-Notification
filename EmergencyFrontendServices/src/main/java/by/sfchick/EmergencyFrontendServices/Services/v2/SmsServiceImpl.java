package by.sfchick.EmergencyFrontendServices.Services.v2;

import by.sfchick.EmergencyFrontendServices.DTO.SmsDTO;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.sfchick.eventsCore.Events.SmsSendEvent;

import java.util.concurrent.ExecutionException;

@Service
public class SmsServiceImpl implements SmsService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SmsServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendSms(SmsDTO smsDTO) throws ExecutionException, InterruptedException {

        SmsSendEvent smsSendEvent = new SmsSendEvent(smsDTO.getContent());

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                "sms-topic",
                smsSendEvent
        );

        SendResult<String, Object> result = kafkaTemplate.send(record).get();

        LOGGER.info("topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("partition: {}", result.getRecordMetadata().partition());
        LOGGER.info("offset: {}", result.getRecordMetadata().offset());

        LOGGER.info("Sms created: {}", smsSendEvent);
    }
}
