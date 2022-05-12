package se.mbi.be2.trava.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!test")
@Service
public class RabbitWorkerService {

    public record Job(String name, int jobNbr) {
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendJob() throws Exception {
        System.out.println("Sending job to worker.");
        var job = new Job("Test", 555);
        var payload = objectMapper.writeValueAsBytes(job);
        rabbitTemplate.convertAndSend(RabbitService.exchangeName, "mailservice.job", payload);
    }

}
