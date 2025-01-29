package by.sfchick.EmergenceSmsServices.util;

import by.sfchick.EmergenceSmsServices.repostitories.SmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class SmsCleanupScheduler {

    private final SmsRepository smsRepository;

    @Autowired
    public SmsCleanupScheduler(SmsRepository smsRepository) {
        this.smsRepository = smsRepository;
    }

    @Scheduled(fixedRate = 43_200_000)
    @Transactional
    public void cleanup() {
        smsRepository.deleteByCreatedAtBefore(LocalDateTime.now());
    }
}
