package by.sfchick.EmergencyNotificationSystem.repositories;

import by.sfchick.EmergencyNotificationSystem.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByNameAndPhoneNumber(String name, String phoneNumber);

    Optional<Person> findByPhoneNumber(String phoneNumber);

    @Query("SELECT p.phoneNumber FROM Person p")
    List<String> findAllPhoneNumbers();

    @Query("SELECT p.email FROM Person p")
    List<String> findAllEmail();

}
