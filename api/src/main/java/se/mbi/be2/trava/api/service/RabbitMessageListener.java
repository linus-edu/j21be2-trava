package se.mbi.be2.trava.api.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener;

import java.nio.charset.StandardCharsets;

public class RabbitMessageListener extends AbstractAdaptableMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) {
        byte[] payload = message.getBody();
        System.out.printf("Got message: %s%n", new String(payload, StandardCharsets.UTF_8));
    }

}