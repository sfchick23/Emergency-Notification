package by.sfchick.EmergencyFrontendServices.Services.v2;

import by.sfchick.EmergencyFrontendServices.DTO.PersonDTO;

import java.util.concurrent.ExecutionException;

public interface PersonService {

    void createPerson(PersonDTO personDTO) throws ExecutionException, InterruptedException;
}
