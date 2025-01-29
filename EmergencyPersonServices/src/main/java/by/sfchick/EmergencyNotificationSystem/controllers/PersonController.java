package by.sfchick.EmergencyNotificationSystem.controllers;

import by.sfchick.EmergencyNotificationSystem.models.Person;
import by.sfchick.EmergencyNotificationSystem.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    private final PersonService personService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<Object> getPersons() {
        try {
            personService.findAll();
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/phone-numbers")
    public ResponseEntity<Object> getPhoneNumbers() {
        return ResponseEntity.ok(personService.findAllPhoneNumbers());
    }

    @GetMapping("/emails")
    public ResponseEntity<Object> getEmails() {
        return ResponseEntity.ok(personService.findAllEmail());
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getPersonsByNameAndPhone(@RequestParam("name") String name, @RequestParam("phone") String phone) {
        try{
            personService.findByNameAndPhone(name, phone);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/search/phone")
    public Optional<Person> getPersonsByPhone(@RequestParam("phone") String phone) {
        return personService.findByPhoneNumber(phone);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createPerson(@RequestBody Person person) {

        try {
            personService.createPerson(person);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editPerson(@PathVariable long id, @RequestBody Person person) {
        try {
            personService.findById(id);
            personService.update(person);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable long id) {
        try {
            personService.delete(id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
