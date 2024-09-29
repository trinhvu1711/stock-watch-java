package com.trinhvu.notification.service;

import com.trinhvu.notification.viewmodel.PaymentConfirmation;
import com.trinhvu.notification.viewmodel.OrderConfirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.trinhvu.notification.model.EmailTemplates.ORDER_CONFIRMATION;
import static com.trinhvu.notification.model.EmailTemplates.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(PaymentConfirmation capturePayment) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.setFrom("admin@contact.com");
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
//        variables.put("customerName", customerName);
        variables.put("amount", capturePayment.amount());
        variables.put("orderReference", capturePayment.orderId());

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());
        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(capturePayment.email());
            mailSender.send(mimeMessage);
            log.info("INFO - Email sent successful {}", capturePayment.email());
        }catch (MessagingException e){
            log.warn("WARNING - cannot send email to {}", capturePayment.email());
        }
    }

    @Async
    public void sendOrderConfirmationEmail(OrderConfirmation orderVm) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.setFrom("admin@contact.com");
        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
//        variables.put("customerName", customerName);
        variables.put("totalAmount", orderVm.totalPrice());
        variables.put("orderReference", orderVm.id());
        variables.put("stocks", orderVm.orderItemVms());

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());
        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(orderVm.email());
            mailSender.send(mimeMessage);
            log.info("INFO - Email sent successful {}", orderVm.email());
        }catch (MessagingException e){
            log.warn("WARNING - cannot send email to {}", orderVm.email());
        }
    }
}
