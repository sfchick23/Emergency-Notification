package by.sfchick.EmergencyEmailServices.controllers;

import by.sfchick.EmergencyEmailServices.models.EmailNotify;
import by.sfchick.EmergencyEmailServices.services.EmailNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/email")
public class EmailNotifyControllers {

    private final EmailNotifyService emailNotifyService;

    @Autowired
    public EmailNotifyControllers(EmailNotifyService emailNotifyService) {
        this.emailNotifyService = emailNotifyService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailNotify emailNotify) {
        try{
            emailNotifyService.sendMockMessage(emailNotify);
            emailNotifyService.sendMessage(emailNotify);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
