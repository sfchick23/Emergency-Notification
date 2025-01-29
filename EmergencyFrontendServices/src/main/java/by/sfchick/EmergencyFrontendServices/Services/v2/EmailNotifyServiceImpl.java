package by.sfchick.EmergencyFrontendServices.Services.v2;

import by.sfchick.EmergencyFrontendServices.DTO.EmailNotifyDTO;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.sfchick.eventsCore.Events.EmailSendEvent;

import java.util.concurrent.ExecutionException;

@Service
public class EmailNotifyServiceImpl implements EmailNotifyService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EmailNotifyServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendEmailNotify(EmailNotifyDTO emailNotifyDTO) throws ExecutionException, InterruptedException {

        EmailSendEvent emailSendEvent = new EmailSendEvent(emailNotifyDTO.getContent());

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                "email-topic",
                emailSendEvent
        );

        SendResult<String, Object> result = kafkaTemplate.send(record).get();

        LOGGER.info("topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("partition: {}", result.getRecordMetadata().partition());
        LOGGER.info("offset: {}", result.getRecordMetadata().offset());

        LOGGER.info("email notify created: {}", emailSendEvent);
    }
}
