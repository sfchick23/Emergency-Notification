package by.sfchick.EmergenceSmsServices.controllers;

import by.sfchick.EmergenceSmsServices.models.Sms;
import by.sfchick.EmergenceSmsServices.services.SmsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/sms")
public class SmsController {

    private final SmsServices smsServices;

    @Autowired
    public SmsController(SmsServices smsServices) {
        this.smsServices = smsServices;
    }

    @PostMapping("send")
    public ResponseEntity<Objects> sendSms(@RequestBody Sms sms){
        try {
            smsServices.sendMockMessages(sms);
//            smsServices.sendMessages(sms);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping()
    public ResponseEntity<List<String>> getPhoneNumbers() {
        List<String> numbers = smsServices.getAllPhoneNumbers();
        return  ResponseEntity.ok().body(numbers);
    }
}
