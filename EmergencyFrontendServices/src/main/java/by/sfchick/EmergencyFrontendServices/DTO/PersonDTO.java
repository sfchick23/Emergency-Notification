package by.sfchick.EmergencyFrontendServices.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 character")
    private String name;

    @Size(min = 2, max = 20, message = "surname should be between 2 and 20 character")
    private String surname;

    @Size(min = 2, max = 20, message = "Phone number should be between 2 and 20 characters (including '+')")
    @Pattern(regexp = "^\\+\\d{1,15}$", message = "Phone number must start with '+' followed by 1 to 15 digits")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    private String email;

}
