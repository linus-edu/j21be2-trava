package se.mbi.be2.trava.api.demo.rabbittemplate;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

//  private CountDownLatch latch = new CountDownLatch(1);

  public void receiveMessage(byte[] message) {
    receiveMessage(new String(message, StandardCharsets.UTF_8));
    // latch.countDown();
  }

  public void receiveMessage(String message) {
    System.out.println("Received <" + message + ">");
  }

//  public CountDownLatch getLatch() {
//    return latch;
//  }

}