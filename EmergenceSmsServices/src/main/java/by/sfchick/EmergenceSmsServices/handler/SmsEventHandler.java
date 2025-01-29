package by.sfchick.EmergenceSmsServices.handler;

import by.sfchick.EmergenceSmsServices.models.Sms;
import by.sfchick.EmergenceSmsServices.repostitories.SmsRepository;
import by.sfchick.EmergenceSmsServices.services.SmsServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.sfchick.eventsCore.Error.NonRetryableException;
import ru.sfchick.eventsCore.Error.RetryableException;
import ru.sfchick.eventsCore.Events.SmsSendEvent;

import java.time.LocalDateTime;
import java.util.List;

@Component
@KafkaListener(topics = "sms-topic")
public class SmsEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final SmsRepository smsRepository;
    private final SmsServices smsServices;

    @Autowired
    public SmsEventHandler(SmsRepository smsRepository, SmsServices smsServices) {
        this.smsRepository = smsRepository;
        this.smsServices = smsServices;
    }

    @Transactional
    @KafkaHandler
    public void handle(SmsSendEvent event) {
        LOGGER.info("get sms event: " + event.getContent());

        if (smsRepository.findByContent(event.getContent()).isPresent()) {
            LOGGER.info("Duplicate Sms send content: {}", event.getContent());
            return;
        }

        Sms sms = new Sms(event.getContent(), LocalDateTime.now());

        try{
            //smsServices.sendMockMessages(sms); Mock Send Message
            smsServices.sendMessages(sms);
            LOGGER.info("send sms event: " + event.getContent());
        }catch (ResourceAccessException e) {
            LOGGER.error(e.getMessage());
            throw new RetryableException(e);
        }catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
            throw new NonRetryableException(e);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new NonRetryableException(e);
        }

        try{
            smsRepository.save(sms);
            LOGGER.info("saved Sms to db");
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            throw new NonRetryableException(e);
        }
    }

}
