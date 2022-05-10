package se.mbi.be2.trava.api.demo.rabbittemplate;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.mbi.be2.trava.api.TravaApiApplication;

@Component
public class RabbitTestSenderRunner implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;
  private final Receiver receiver;

  public RabbitTestSenderRunner(Receiver receiver, RabbitTemplate rabbitTemplate) {
    this.receiver = receiver;
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Sending message...");
    rabbitTemplate.convertAndSend(TravaApiApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
//    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
  }

}