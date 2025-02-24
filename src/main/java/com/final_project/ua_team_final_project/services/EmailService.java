package com.final_project.ua_team_final_project.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.io.OutputStream;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailWithCsv(String toEmail, String subject, String message, Map<String, OutputStream> files)
            throws MessagingException, IOException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(message);

        for (Map.Entry<String, OutputStream> entry : files.entrySet()) {
            String fileName = entry.getKey();
            ByteArrayOutputStream outputStream = (ByteArrayOutputStream) entry.getValue();
            InputStreamSource attachment = new ByteArrayResource(outputStream.toByteArray());

            helper.addAttachment(fileName, attachment);
        }

        mailSender.send(mimeMessage);
    }
}
