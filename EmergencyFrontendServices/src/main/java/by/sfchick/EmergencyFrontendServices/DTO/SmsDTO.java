package by.sfchick.EmergencyFrontendServices.DTO;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsDTO {

    @Size(min = 2, max = 300, message = "Content should be between 2 and 300 character")
    private String content;

}
