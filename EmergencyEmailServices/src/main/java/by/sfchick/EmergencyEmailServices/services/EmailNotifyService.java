package by.sfchick.EmergencyEmailServices.services;

import by.sfchick.EmergencyEmailServices.models.EmailNotify;
import by.sfchick.EmergencyEmailServices.repositories.EmailNotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EmailNotifyService {

    private final EmailNotifyRepository emailNotifyRepository;
    private final MailService mailService;
    private final RestTemplate restTemplate;

    @Value("${person.service.url}")
    private String URL;

    @Autowired
    public EmailNotifyService(EmailNotifyRepository emailNotifyRepository, MailService mailService, RestTemplate restTemplate) {
        this.emailNotifyRepository = emailNotifyRepository;
        this.mailService = mailService;
        this.restTemplate = restTemplate;
    }

    public Optional<EmailNotify> findByContent(String content) {
        return emailNotifyRepository.findByContent(content);
    }

    public List<String> getAllEmail(){
        ResponseEntity<List<String>> response = restTemplate.exchange(
                URL + "/emails",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        );
        System.out.println(response.getBody());
        return response.getBody();
    }

    @Transactional
    public void sendMockMessage(EmailNotify emailNotify) {
        List<String> emails = getAllEmail();

        emailNotify.setCreatedAt(LocalDateTime.now());

        for (String email : emails) {
            System.out.println("Sending a message to a email: " + email);
            System.out.println("content: " + emailNotify.getContent());
        }

        emailNotifyRepository.save(emailNotify);
    }

    @Transactional
    public void sendMessage(EmailNotify emailNotify) {
        List<String> emails = getAllEmail();

        emailNotify.setCreatedAt(LocalDateTime.now());

        for (String email : emails) {
            mailService.sendEmail(email, "Emergency Notification", emailNotify.getContent());
        }

        emailNotifyRepository.save(emailNotify);
    }

    @Transactional
    public void save(EmailNotify emailNotify) {
        emailNotifyRepository.save(emailNotify);
    }

}




