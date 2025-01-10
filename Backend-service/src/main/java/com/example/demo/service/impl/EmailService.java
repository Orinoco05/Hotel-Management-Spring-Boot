//package com.example.demo.service.impl;
//
//import com.sendgrid.Method;
//import com.sendgrid.Request;
//import com.sendgrid.Response;
//import com.sendgrid.SendGrid;
//import com.sendgrid.helpers.mail.Mail;
//import com.sendgrid.helpers.mail.objects.Content;
//import com.sendgrid.helpers.mail.objects.Email;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j(topic = "EMAIL-SERVICE")
//public class EmailService {
//
//    @Value("${spring.sendgrid.from-email}")
//    private String fromMail;
//
//    private final SendGrid sendGrid;
//
//    /**
//     * Send Email by Sendgrid
//     * @param to send email to someone
//     * @param subject
//     * @param text
//     */
//    public void send(String to, String subject, String text) {
//        log.info("Sending email to " + to);
//        Email fromEmail = new Email(fromMail);
//        Email toEmail = new Email(to);
//
//        Content content = new Content("text/plain", text);
//        Mail mail = new Mail(fromEmail, subject, toEmail, content);
//
//        Request request = new Request();
//
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//
//            Response response = sendGrid.api(request);
//            if (response.getStatusCode() != 202) {
//                log.error("Error sending email to ");
//            } else{
//                log.info("Successfully sent email to " );
//            }
//        } catch (IOException e) {
//            log.error("Error sending email to, error: " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//}
