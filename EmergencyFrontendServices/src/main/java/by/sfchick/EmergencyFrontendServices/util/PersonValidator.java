package by.sfchick.EmergencyFrontendServices.util;

import by.sfchick.EmergencyFrontendServices.DTO.PersonDTO;
import by.sfchick.EmergencyFrontendServices.Services.v1.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PersonDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonDTO person = (PersonDTO) target;

        if (personService.findByPhone(person.getPhoneNumber()).isPresent()) {
            errors.rejectValue("phoneNumber", "", "this phone number is already in use");
        }
    }

}
