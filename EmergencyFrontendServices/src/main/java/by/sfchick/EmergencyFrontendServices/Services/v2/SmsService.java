package by.sfchick.EmergencyFrontendServices.Services.v2;

import by.sfchick.EmergencyFrontendServices.DTO.SmsDTO;

import java.util.concurrent.ExecutionException;

public interface SmsService {
    void sendSms(SmsDTO smsDTO) throws ExecutionException, InterruptedException;
}
