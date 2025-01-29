package by.sfchick.EmergencyFrontendServices.Services.v2;

import by.sfchick.EmergencyFrontendServices.DTO.PersonDTO;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.sfchick.eventsCore.Events.PersonCreateEvent;

import java.util.concurrent.ExecutionException;

@Service
public class PersonServiceImpl implements PersonService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PersonServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void createPerson(PersonDTO personDTO) throws ExecutionException, InterruptedException {

        PersonCreateEvent personCreateEvent = new PersonCreateEvent(personDTO.getName(), personDTO.getSurname(), personDTO.getEmail(), personDTO.getPhoneNumber());

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                "persons-topic",
                personCreateEvent
        );


//        record.headers().add("phoneNumber", personDTO.getPhoneNumber().getBytes());

        SendResult<String, Object> result = kafkaTemplate.send(record).get();

        LOGGER.info("topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("partition: {}", result.getRecordMetadata().partition());
        LOGGER.info("offset: {}", result.getRecordMetadata().offset());

        LOGGER.info("Person created: {}", personCreateEvent);

    }
}
