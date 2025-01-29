package by.sfchick.EmergenceSmsServices.repostitories;

import by.sfchick.EmergenceSmsServices.models.Sms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SmsRepository extends JpaRepository<Sms, Long> {
    void deleteByCreatedAtBefore(LocalDateTime timestamp);

    Optional<Sms> findByContent(String content);
}
