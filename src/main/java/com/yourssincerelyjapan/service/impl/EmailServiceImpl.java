package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.config.EmailConfiguration;
import com.yourssincerelyjapan.model.entity.Email;
import com.yourssincerelyjapan.repository.EmailRepository;
import com.yourssincerelyjapan.service.EmailService;
import com.yourssincerelyjapan.utils.EmailUtils;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import static com.yourssincerelyjapan.constant.EmailConstant.*;

@Service
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

/*    @Override
    public void sendMail(String emailTo, String subject, String emailContent) throws MessagingException, UnsupportedEncodingException {

        final MimeMessage message = this.javaMailSender.createMimeMessage();

        final MimeMessageHelper helper = new MimeMessageHelper(message, true);

        final InternetAddress fromAddress = new InternetAddress(this.emailConfiguration.getUsername(), "Yours Sincerely Japan");

        helper.setFrom(fromAddress);
        helper.setTo(emailTo);
        helper.setSubject(subject);
        helper.setText(emailContent);

        this.javaMailSender.send(message);
    }*/

    @Override
    public void sendSimpleVerificationEmail(String name, String emailTo, String token) throws UnsupportedEncodingException {

        final InternetAddress fromAddress = new InternetAddress(this.emailConfiguration.getUsername(), "Yours Sincerely Japan");
        final MimeMessage message = this.javaMailSender.createMimeMessage();

        try {

            final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setPriority(1);
            helper.setFrom(fromAddress);
            helper.setSubject(SUBJECT_WELCOME);
            helper.setTo(emailTo);
            helper.setText(EmailUtils.getEmailMessage(name, token));

            this.javaMailSender.send(message);

            final Email sentEmail = Email
                    .builder()
                    .fromEmail(this.emailConfiguration.getUsername())
                    .toEmail(emailTo)
                    .subjectEmail(SUBJECT_WELCOME)
                    .contentEmail(EmailUtils.getEmailMessage(name, token))
                    .createdOn(LocalDateTime.now())
                    .sent(true)
                    .build();

            this.emailRepository.save(sentEmail);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void sendMimeEmailWithAttachments(String name, String to, String token) throws UnsupportedEncodingException {

    }

    @Override
    @Async
    public void sendHtmlEmail(String name, String emailTo, String token) throws UnsupportedEncodingException {

        final InternetAddress fromAddress = new InternetAddress(this.emailConfiguration.getUsername(), "Yours Sincerely Japan");
        final MimeMessage message = this.javaMailSender.createMimeMessage();

        try {

            final Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("url", EmailUtils.getVerificationUrl(token));

            String text = templateEngine.process("verify-email", context);

            final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setPriority(1);
            helper.setFrom(fromAddress);
            helper.setSubject(SUBJECT_WELCOME);
            helper.setTo(emailTo);
            helper.setText(text, true);

            this.javaMailSender.send(message);

            final Email sentEmail = Email
                    .builder()
                    .fromEmail(this.emailConfiguration.getUsername())
                    .toEmail(emailTo)
                    .subjectEmail(SUBJECT_WELCOME)
                    .contentEmail(EmailUtils.getEmailMessage(name, token))
                    .createdOn(LocalDateTime.now())
                    .sent(true)
                    .build();

            this.emailRepository.save(sentEmail);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
