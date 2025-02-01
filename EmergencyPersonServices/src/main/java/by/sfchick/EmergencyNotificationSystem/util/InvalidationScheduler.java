package by.sfchick.EmergencyNotificationSystem.util;

import by.sfchick.EmergencyNotificationSystem.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InvalidationScheduler {

    private final PersonService personService;

    @Autowired
    public InvalidationScheduler(PersonService personService) {
        this.personService = personService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void invalidate() {
        personService.clearAllCaches();
    }
}
