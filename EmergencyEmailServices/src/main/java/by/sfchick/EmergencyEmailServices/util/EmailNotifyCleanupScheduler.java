package by.sfchick.EmergencyEmailServices.util;

import by.sfchick.EmergencyEmailServices.repositories.EmailNotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class EmailNotifyCleanupScheduler {

    private final EmailNotifyRepository emailNotifyRepository;
  
    @Autowired
    public EmailNotifyCleanupScheduler(EmailNotifyRepository emailNotifyRepository) {
        this.emailNotifyRepository = emailNotifyRepository;
    }

    @Scheduled(fixedRate = 43_200_000)
    @Transactional
    public void cleanup() {
        emailNotifyRepository.deleteByCreatedAtBefore(LocalDateTime.now());
    }
}
