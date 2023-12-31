package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.config.EmailConfiguration;
import com.yourssincerelyjapan.model.dto.ContactDTO;
import com.yourssincerelyjapan.model.entity.Email;
import com.yourssincerelyjapan.repository.EmailRepository;
import com.yourssincerelyjapan.service.EmailService;
import com.yourssincerelyjapan.utils.EmailUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import static com.yourssincerelyjapan.constant.EmailConstant.*;

@Service
@Configuration
@EnableAsync
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final EmailConfiguration emailConfiguration;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(EmailRepository emailRepository, EmailConfiguration emailConfiguration,
                            JavaMailSender javaMailSender, TemplateEngine templateEngine) {

        this.emailRepository = emailRepository;
        this.emailConfiguration = emailConfiguration;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void sendSimpleEmail(ContactDTO contactDTO) {

        try {

            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();

            final MimeMessageHelper mimeMessageHelper = getMimeMessageHelper(contactDTO, mimeMessage);

            this.javaMailSender.send(mimeMessageHelper.getMimeMessage());

            final Email sentEmail = Email
                    .builder()
                    .emailFrom(contactDTO.getEmail())
                    .emailTo(this.emailConfiguration.getUsername())
                    .emailSubject(String.format(MESSAGE_FROM, contactDTO.getFullName()))
                    .emailContent(contactDTO.getContactContent())
                    .receivedOn(LocalDateTime.now())
                    .received(true)
                    .build();

            this.emailRepository.save(sentEmail);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    @Override
    @Async
    public void sendHtmlVerificationEmail(String name, String emailTo, String token) throws UnsupportedEncodingException {

        final InternetAddress fromAddress = new InternetAddress(this.emailConfiguration.getUsername(), YOURS_SINCERELY_JAPAN);

        final MimeMessage message = this.javaMailSender.createMimeMessage();

        try {

            final Context context = new Context();
            context.setVariable(NAME, name);
            context.setVariable(URL, EmailUtils.getVerificationUrl(token));

            final String text = this.templateEngine.process(VERIFY_EMAIL_TEMPLATE, context);

            final MimeMessageHelper helper = new MimeMessageHelper(message, true, EMAIL_ENCODING);

            helper.setPriority(1);
            helper.setFrom(fromAddress);
            helper.setSubject(SUBJECT_WELCOME);
            helper.setTo(emailTo);
            helper.setText(text, true);

            this.javaMailSender.send(message);

            final Email sentEmail = Email
                    .builder()
                    .emailFrom(this.emailConfiguration.getUsername())
                    .emailTo(emailTo)
                    .emailSubject(SUBJECT_WELCOME)
                    .emailContent(EmailUtils.getEmailMessage(name, token))
                    .createdOn(LocalDateTime.now())
                    .sent(true)
                    .build();

            this.emailRepository.save(sentEmail);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private MimeMessageHelper getMimeMessageHelper(ContactDTO contactDTO, MimeMessage mimeMessage) throws MessagingException {

        final InternetAddress emailFrom = new InternetAddress(contactDTO.getEmail());

        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setSubject(String.format(MESSAGE_FROM, contactDTO.getFullName()));
        mimeMessageHelper.setFrom(emailFrom);
        mimeMessageHelper.setTo(this.emailConfiguration.getUsername());
        mimeMessageHelper.setText(contactDTO.getContactContent());
        mimeMessageHelper.setReplyTo(emailFrom);

        return mimeMessageHelper;
    }
}
