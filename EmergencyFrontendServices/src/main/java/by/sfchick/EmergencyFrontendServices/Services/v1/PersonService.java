package by.sfchick.EmergencyFrontendServices.Services.v1;

import by.sfchick.EmergencyFrontendServices.DTO.PersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    @Value("${person.service.url}")
    private String URL;
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate;

    @Autowired
    public PersonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<PersonDTO> findByPhone(String phone) {
        String url = URL + "/search/phone?phone=" + phone;
        ResponseEntity<PersonDTO> response = restTemplate.getForEntity(url, PersonDTO.class);
        return Optional.ofNullable(response.getBody());
    }

    @Transactional
    public void createPerson(PersonDTO personDTO) {
        String url = URL + "/create";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PersonDTO> entity = new HttpEntity<>(personDTO, headers);

        try{
            ResponseEntity<PersonDTO> createPersonResponse = restTemplate.postForEntity(url, entity, PersonDTO.class);
            LOGGER.info("createPersonResponse: {}", createPersonResponse);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }

}
