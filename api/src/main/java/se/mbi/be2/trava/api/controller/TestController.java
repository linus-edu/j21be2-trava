package se.mbi.be2.trava.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.mbi.be2.trava.api.service.RabbitWorkerService;

@RestController
public class TestController {

    @Autowired
    RabbitWorkerService rabbitWorkerService;

    @Autowired
    BuildProperties buildProperties;

    @GetMapping("/")
    public String root() {
        return String.format("This is Trava! Version=%s", buildProperties.getVersion());
    }

//    @GetMapping("/hello")
//    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
//        return String.format("Hello %s! This is the first version", buildProperties.getVersion());
//    }

    @GetMapping("/message-worker")
    public String messageWorker() throws Exception {
        rabbitWorkerService.sendJob();
        return "ok";
    }

    @GetMapping("/message-worker-public")
    public String messageWorkerPublic() throws Exception {
        rabbitWorkerService.sendJob();
        return "ok";
    }

}
