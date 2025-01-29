package by.sfchick.EmergencyEmailServices.services;

import by.sfchick.EmergencyEmailServices.models.EmailNotify;
import by.sfchick.EmergencyEmailServices.repositories.EmailNotifyRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8"); // Указываем кодировку

            // Формируем тело письма
            String emailContent = String.format(
                    content
            );

            // Устанавливаем параметры письма
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(emailContent, true); // true для HTML контента

            // Отправляем письмо
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending letter: " + e.getMessage(), e);
        }
    }
}
