package by.sfchick.EmergencyFrontendServices.Services.v1;

import by.sfchick.EmergencyFrontendServices.DTO.SmsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
public class SmsService {

    @Value("${sms.service.url}")
    private String URL;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;

    @Autowired
    public SmsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void sendSms(SmsDTO smsDTO) {
        String url = URL + "/send";
        try{
            restTemplate.postForObject(url, smsDTO, SmsDTO.class);
            LOGGER.info("SMS SENT");
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }

    }
}
