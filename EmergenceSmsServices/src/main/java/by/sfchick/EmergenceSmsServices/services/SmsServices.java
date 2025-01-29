package by.sfchick.EmergenceSmsServices.services;

import by.sfchick.EmergenceSmsServices.models.Sms;
import by.sfchick.EmergenceSmsServices.repostitories.SmsRepository;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SmsServices {

    private final SmsRepository smsRepository;
    private final RestTemplate restTemplate;

    @Value("${person.service.url}")
    private String URL;

    @Value("${api.key}")
    private String API_KEY;

    @Value("${api.mail}")
    private String API_EMAIL;

    @Autowired
    public SmsServices(SmsRepository smsRepository, RestTemplate restTemplate) {
        this.smsRepository = smsRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void sendMockMessages(Sms sms) {

        List<String> phoneNumbers = getAllPhoneNumbers();

        sms.setCreatedAt(LocalDateTime.now());

        for (String phoneNumber : phoneNumbers) {
            System.out.println("Sending a message to a number:" + phoneNumber);
            System.out.println("Content: " + sms.getContent());
        }

        smsRepository.save(sms);

    }

    @Transactional
    public void sendMessages(Sms sms) {

        List<String> phoneNumbers = getAllPhoneNumbers();

        sms.setCreatedAt(LocalDateTime.now());

        System.out.println(sms.getContent());

        for (String phoneNumber : phoneNumbers) {
            sendToApiSms(phoneNumber, sms);
        }

        smsRepository.save(sms);
    }


    public List<String> getAllPhoneNumbers() {
        ResponseEntity<List<String>> response = restTemplate.exchange(
                URL + "/phone-numbers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        );
        System.out.println("вызов метода getAllPhoneNumbers()");
        return response.getBody();
    }

    private void sendToApiSms(String phoneNumber, Sms sms) {
        String apiUrl = "https://gate.smsaero.ru/v2/sms/send";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((API_EMAIL + ":" + API_KEY).getBytes()));
        headers.set("Content-Type", "application/json");

        String body = String.format("{\"number\":\"%s\"," +
                                    "\"text\":\"%s\"," +
                                    "\"sign\":\"SMS Aero\"}",
                                    phoneNumber, sms.getContent());
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("The message was successfully sent to the number: " + phoneNumber);
        } else {
            System.out.println("Error when sending a message to the number:" + phoneNumber + ". Response: " + response.getBody());
        }
    }

}
