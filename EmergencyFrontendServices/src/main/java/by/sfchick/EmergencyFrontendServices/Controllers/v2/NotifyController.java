package by.sfchick.EmergencyFrontendServices.Controllers.v2;

import by.sfchick.EmergencyFrontendServices.DTO.EmailNotifyDTO;
import by.sfchick.EmergencyFrontendServices.DTO.PersonDTO;

import by.sfchick.EmergencyFrontendServices.DTO.SmsDTO;
import by.sfchick.EmergencyFrontendServices.Services.v2.EmailNotifyService;
import by.sfchick.EmergencyFrontendServices.Services.v2.PersonService;

import by.sfchick.EmergencyFrontendServices.Services.v2.SmsService;
import by.sfchick.EmergencyFrontendServices.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutionException;

@Controller("v2")
@RequestMapping("/notify/v2")
public class NotifyController {

    private final PersonService personService; //v2 person Service if exception that check imports;
    private final SmsService smsService;
    private final PersonValidator personValidator;
    private final EmailNotifyService emailNotifyService;

    @Autowired
    public NotifyController(PersonService personService, SmsService smsService, PersonValidator personValidator, EmailNotifyService emailNotifyService) {
        this.personService = personService;
        this.smsService = smsService;
        this.personValidator = personValidator;
        this.emailNotifyService = emailNotifyService;
    }


    @GetMapping("/person")
    public String notify(Model model) {
        model.addAttribute("person", new PersonDTO());
        return "pages/v2/notify";
    }

    @PostMapping("/person")
    public String createPerson(@ModelAttribute("person") @Valid PersonDTO personDTO, BindingResult bindingResult) throws ExecutionException, InterruptedException {
        personValidator.validate(personDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "pages/v2/notify";
        }

        personService.createPerson(personDTO);

        return "pages/v2/successfully";
    }

    @GetMapping("/send-sms")
    public String createSms(Model model) {
        model.addAttribute("sms", new SmsDTO());
        return "pages/v2/sms-create";
    }

    @PostMapping("/sms")
    public String createSms(@ModelAttribute("sms") @Valid SmsDTO smsDTO, BindingResult bindingResult) throws ExecutionException, InterruptedException {
        System.out.println("smsDTO: " + smsDTO);
        if (bindingResult.hasErrors()) {
            return "pages/v2/sms-create";
        }

        smsService.sendSms(smsDTO);

        return "pages/v2/successfully";
    }

    @GetMapping("/send-email")
    public String createEmail(Model model) {
        model.addAttribute("email", new EmailNotifyDTO());
        return "pages/v2/email-create";
    }

    @PostMapping("/email")
    public String createEmailNotify(@ModelAttribute("email") @Valid EmailNotifyDTO emailDTO, BindingResult bindingResult) throws ExecutionException, InterruptedException {
        if (bindingResult.hasErrors()) {
            return "pages/v2/email-create";
        }

        emailNotifyService.sendEmailNotify(emailDTO);

        return "pages/v2/successfully";
    }
}
