package by.sfchick.EmergencyEmailServices.handler;

import by.sfchick.EmergencyEmailServices.models.EmailNotify;
import by.sfchick.EmergencyEmailServices.repositories.EmailNotifyRepository;
import by.sfchick.EmergencyEmailServices.services.EmailNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import ru.sfchick.eventsCore.Error.NonRetryableException;
import ru.sfchick.eventsCore.Error.RetryableException;
import ru.sfchick.eventsCore.Events.EmailSendEvent;
import ru.sfchick.eventsCore.Events.SmsSendEvent;

import java.time.LocalDateTime;

@Component
@KafkaListener(topics = "email-topic")
public class EmailEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final EmailNotifyService emailNotifyService;

    @Autowired
    public EmailEventHandler(EmailNotifyService emailNotifyService) {
        this.emailNotifyService = emailNotifyService;
    }

    @Transactional
    @KafkaHandler
    public void handle(EmailSendEvent event) {
        LOGGER.info("get email event: " + event.getContent());
        System.out.println(event.getContent());

        if (emailNotifyService.findByContent(event.getContent()).isPresent()) {
            LOGGER.info("Duplicate email send content: {}", event.getContent());
            return;
        }

        EmailNotify emailNotify = new EmailNotify(event.getContent(), LocalDateTime.now());

        try{
//            emailNotifyService.sendMockMessage(emailNotify); Mock Send Message
            emailNotifyService.sendMessage(emailNotify);
            LOGGER.info("Email sent successfully");
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
            emailNotifyService.save(emailNotify);
            LOGGER.info("saved email to db");
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            throw new NonRetryableException(e);
        }

    }

}
