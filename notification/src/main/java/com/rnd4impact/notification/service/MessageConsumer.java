package com.rnd4impact.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rnd4impact.notification.mapping.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class MessageConsumer {

    @Autowired
    private JavaMailSender emailSender;

    @KafkaListener(topics = "notifications", groupId = "groupId")
    void listener(String data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message message = mapper.readValue(data, Message.class);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("library@rnd4impact.com");
            msg.setTo(message.customerEmail);
            msg.setSubject("Your rental details");
            if (message.messageType.equals(Message.Type.BOOK_RENTED)) {
                msg.setText(String.format(
                    "This is to confirm that you rented: \"%s\", return due will be three days after", message.book)
                );
            }
            else if (message.messageType.equals(Message.Type.BOOK_RETURNED)) {
                msg.setText(String.format(
                    "This is to confirm that you returned: \"%s\"",
                    message.book)
                );
            }
            emailSender.send(msg);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
