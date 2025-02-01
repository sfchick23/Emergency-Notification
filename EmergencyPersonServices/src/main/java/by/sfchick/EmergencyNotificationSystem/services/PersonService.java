package by.sfchick.EmergencyNotificationSystem.services;

import by.sfchick.EmergencyNotificationSystem.models.Person;
import by.sfchick.EmergencyNotificationSystem.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;
    private static final Logger log = LoggerFactory.getLogger(PersonService.class);
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findById(long id) {
        return personRepository.findById(id).orElse(null);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Cacheable(value = "phoneNumbers", unless = "#result == null or #result.isEmpty()")
    public List<String> findAllPhoneNumbers() {
        return personRepository.findAllPhoneNumbers();
    }

    public Optional<Person> findByNameAndPhone(String name, String phone) {
        return personRepository.findByNameAndPhoneNumber(name, phone);
    }
    @Cacheable(value = "AllEmails", unless = "#result == null or #result.isEmpty()")
    public List<String> findAllEmail(){
        return personRepository.findAllEmail();
    }

    public Optional<Person> findByPhoneNumber(String phoneNumber) {
        return personRepository.findByPhoneNumber(phoneNumber);
    }

    @Transactional
    public void delete(long id) {
        Person person = personRepository.findById(id).orElse(null);
        personRepository.delete(Objects.requireNonNull(person));
    }

    @Transactional
    public void update(Person person) {
        personRepository.save(person);
    }

    @Transactional
    @CacheEvict(value = {"phoneNumbers", "AllEmails"},  allEntries = true)
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @CacheEvict(value = {"phoneNumbers", "AllEmails"},  allEntries = true)
    public void clearAllCaches(){
        log.info("Clearing all caches");
    }
}
