package by.sfchick.EmergencyFrontendServices.Services.v1;


import by.sfchick.EmergencyFrontendServices.DTO.EmailNotifyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
public class EmailNotifyService {

    @Value("${email.service.url}")
    private String URL;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    private final RestTemplate restTemplate;

    @Autowired
    public EmailNotifyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void sendEmailNotify(EmailNotifyDTO emailNotifyDTO) {
        String url = URL + "/send";
        try {
            restTemplate.postForObject(url, emailNotifyDTO, EmailNotifyDTO.class);
            LOGGER.info("Email notification sent successfully");
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }
}
