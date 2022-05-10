package se.mbi.be2.trava.api.controller;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class MqttService {

    private IMqttClient client;

    @PostConstruct
    private void conenctClient() throws MqttException {
        String publisherId = UUID.randomUUID().toString();
        // String publisherId = "client-id-123";
        IMqttClient newClient = new MqttClient("tcp://broker.mqttdashboard.com:1883", publisherId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        newClient.connect(options);

        newClient.subscribe("chat-demo/#", (topic, mqttMsg) -> {
            byte[] payload = mqttMsg.getPayload();
            var payloadMsg = new String(payload, StandardCharsets.UTF_8);
            System.out.println("Received: " + payloadMsg);
        });

        client = newClient;
    }

    public void publish(String topic, String msgString) throws MqttException {
        byte[] payload = msgString.getBytes();
        MqttMessage mqttMessage = new MqttMessage(payload);
        mqttMessage.setQos(0);
        mqttMessage.setRetained(true);
        client.publish(topic, mqttMessage);
    }

}
