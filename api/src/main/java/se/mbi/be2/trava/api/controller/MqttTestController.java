package se.mbi.be2.trava.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttTestController {

    @Autowired
    MqttService mqttService;

    @GetMapping("/test-send")
    public void testSend(@RequestParam() String msgString) throws Exception {
        mqttService.publish("chat-demo/everyone", msgString);
    }
}
