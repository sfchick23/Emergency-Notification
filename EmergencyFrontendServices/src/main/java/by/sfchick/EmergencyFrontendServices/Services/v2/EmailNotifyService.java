package by.sfchick.EmergencyFrontendServices.Services.v2;

import by.sfchick.EmergencyFrontendServices.DTO.EmailNotifyDTO;
import by.sfchick.EmergencyFrontendServices.DTO.SmsDTO;

import java.util.concurrent.ExecutionException;

public interface EmailNotifyService {
    void sendEmailNotify(EmailNotifyDTO emailNotifyDTO) throws ExecutionException, InterruptedException;
}
