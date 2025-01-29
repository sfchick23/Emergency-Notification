package by.sfchick.EmergencyFrontendServices.Controllers.v1;

import by.sfchick.EmergencyFrontendServices.DTO.EmailNotifyDTO;
import by.sfchick.EmergencyFrontendServices.DTO.PersonDTO;
import by.sfchick.EmergencyFrontendServices.DTO.SmsDTO;
import by.sfchick.EmergencyFrontendServices.Services.v1.EmailNotifyService;
import by.sfchick.EmergencyFrontendServices.Services.v1.PersonService;
import by.sfchick.EmergencyFrontendServices.Services.v1.SmsService;
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

@Controller("v1")
@RequestMapping("/notify/v1")
public class NotifyController {

    private final PersonService personService; //v1 person Service if exception that check imports
    private final PersonValidator personValidator;
    private final SmsService smsService;
    private final EmailNotifyService emailNotifyService;

    @Autowired
    public NotifyController(PersonService personService, PersonValidator personValidator, SmsService smsService, EmailNotifyService emailNotifyService) {
        this.personService = personService;
        this.personValidator = personValidator;
        this.smsService = smsService;
        this.emailNotifyService = emailNotifyService;
    }

    @GetMapping("/person")
    public String notify(Model model) {
        model.addAttribute("person", new PersonDTO());
        return "pages/v1/notify";
    }

    @PostMapping("/person")
    public String createPerson(@ModelAttribute("person") @Valid PersonDTO personDTO, BindingResult bindingResult) {
        System.out.println(personDTO);
        personValidator.validate(personDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "pages/v1/notify";
        }

        personService.createPerson(personDTO);
        return "pages/v1/successfully";
    }

    @GetMapping("/send-sms")
    public String createSms(Model model) {
        model.addAttribute("sms", new SmsDTO());
        return "pages/v1/sms-create";
    }

    @PostMapping("/sms")
    public String createSms(@ModelAttribute("sms") @Valid SmsDTO smsDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "pages/v1/sms-create";
        }

        smsService.sendSms(smsDTO);

        return "pages/v1/successfully";
    }

    @GetMapping("/send-email")
    public String createEmail(Model model) {
        model.addAttribute("email", new EmailNotifyDTO());
        return "pages/v1/email-create";
    }

    @PostMapping("/email")
    public String createEmailNotify(@ModelAttribute("email") @Valid EmailNotifyDTO emailDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/v1/email-create";
        }

        emailNotifyService.sendEmailNotify(emailDTO);

        return "pages/v1/successfully";
    }
}
