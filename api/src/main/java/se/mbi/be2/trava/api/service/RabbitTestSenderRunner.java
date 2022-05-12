package se.mbi.be2.trava.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.mbi.be2.trava.api.security.JwtTokenService;

@Component
public class RabbitTestSenderRunner implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenService.class);

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitService rabbitService;

    @Override
    public void run(String... args) {
        System.out.println("Sending message...");
        try {
            // rabbitTemplate.convertAndSend(RabbitService.exchangeName, "api.info", "API started");
            rabbitService.sendToTopic("api.info", "API started");
        } catch (AmqpException e) {
            LOG.error("Failed to send message to RabbitMQ", e);
        }
    }

}