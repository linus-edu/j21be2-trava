package se.mbi.be2.trava.api.controller.api;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.mbi.be2.trava.api.service.RestTemplateDemoService;
import se.mbi.be2.trava.api.service.RabbitWorkerService;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @Autowired
    BuildProperties buildProperties;

    @Autowired
    RabbitWorkerService rabbitWorkerService;

    @Autowired
    RestTemplateDemoService getDemoService;

    @GetMapping("/hello")
    public String root(@AuthenticationPrincipal Object user) {
        return String.format("This is Trava! Version=%s You are %s", buildProperties.getVersion(), user);
    }

    @GetMapping("/rabbitmq")
    public String messageWorker() throws Exception {
        rabbitWorkerService.sendJob();

        // Exempel på egen räknare. Vi kan lägga till specifika fält till räknaren.
        Counter counter = Metrics.counter("rabbitmq.sent-jobs", "tag1", "tag-value-1");
        counter.increment();

        return "ok";
    }

    @GetMapping("/resttemplate")
    public String getDemo() {
        return getDemoService.getPostsPlainJSON();
    }

}
