package by.sfchick.EmergencyNotificationSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 character")
    private String name;

    @Column(name = "surname")
    @Size(min = 2, max = 50, message = "surname should be between 2 and 50 character")
    private String surname;

    @Column(name = "phone_number")
    @Size(min = 2, max = 20, message = "Phone number should be between 2 and 20 digits")
    private String phoneNumber;

    @Column(name = "email")
    @Email(message = "Email should be valid")
    private String email;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Person(String name, String surname, String phoneNumber, String email) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 2, max = 50, message = "Name should be between 2 and 50 character") String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 50, message = "Name should be between 2 and 50 character") String name) {
        this.name = name;
    }

    public @Size(min = 2, max = 50, message = "surname should be between 2 and 50 character") String getSurname() {
        return surname;
    }

    public void setSurname(@Size(min = 2, max = 50, message = "surname should be between 2 and 50 character") String surname) {
        this.surname = surname;
    }

    public @Size(min = 2, max = 20, message = "Phone number should be between 2 and 20 digits") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Size(min = 2, max = 20, message = "Phone number should be between 2 and 20 digits") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") String email) {
        this.email = email;
    }
}
