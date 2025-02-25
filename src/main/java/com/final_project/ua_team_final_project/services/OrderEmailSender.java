package com.final_project.ua_team_final_project.services;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Service
public class OrderEmailSender {

    private final EmailService emailService;

    public OrderEmailSender(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendSupplierOrder(String supplierEmail, Map<String, OutputStream> csvFiles) {
        String subject = "New Supplier Order";
        String message = "Please find the attached supplier order CSV file.";

        try {
            emailService.sendEmailWithCsv(supplierEmail, subject, message, csvFiles);
            System.out.println("Email sent successfully to " + supplierEmail);
        } catch (MessagingException | IOException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
