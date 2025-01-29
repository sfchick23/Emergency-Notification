package by.sfchick.EmergencyNotificationSystem.handler;

import ru.sfchick.eventsCore.Error.NonRetryableException;
import by.sfchick.EmergencyNotificationSystem.models.Person;
import by.sfchick.EmergencyNotificationSystem.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sfchick.eventsCore.Error.NonRetryableException;
import ru.sfchick.eventsCore.Events.PersonCreateEvent;

import java.util.Optional;

@Component
@KafkaListener(topics = "persons-topic")
public class PersonEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final PersonService personService;

    @Autowired
    public PersonEventHandler(PersonService personService) {
        this.personService = personService;
    }

    @Transactional
    @KafkaHandler
    public void handle(PersonCreateEvent event) {
        LOGGER.info("received product created event name: " + event.getName());
        LOGGER.info("received product created event phoneNumber: " + event.getPhoneNumber());
        LOGGER.info("received product created event email: " + event.getEmail());

        Optional<Person> checkPerson = personService.findByPhoneNumber(event.getPhoneNumber());
        if (checkPerson.isPresent()) {
            LOGGER.info("Duplicate request: {}", event);
            return;
        }

        try {
            personService.createPerson(new Person(event.getName(), event.getSurname(), event.getPhoneNumber(), event.getEmail()));
        }catch (DataIntegrityViolationException e) {
            LOGGER.error(e.getMessage());
            throw new NonRetryableException(e);
        }

    }
}
