package by.sfchick.EmergencyEmailServices.repositories;

import by.sfchick.EmergencyEmailServices.models.EmailNotify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailNotifyRepository extends JpaRepository<EmailNotify, Long> {
    void deleteByCreatedAtBefore(LocalDateTime timestamp);

    Optional<EmailNotify> findByContent(String content);
}
