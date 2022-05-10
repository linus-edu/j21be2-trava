package se.mbi.be2.trava.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.mbi.be2.trava.api.service.RabbitWorkerService;

@RestController
public class TestController {

    @Autowired
    RabbitWorkerService rabbitWorkerService;

    @GetMapping("/message-worker")
    public String getUsers() throws Exception {
        rabbitWorkerService.sendJob();
        return "ok";
    }
}
