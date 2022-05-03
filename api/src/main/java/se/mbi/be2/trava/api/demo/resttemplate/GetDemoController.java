package se.mbi.be2.trava.api.demo.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetDemoController {

    @Autowired
    GetDemoService getDemoService;

    @GetMapping("/demo/get")
    public String getDemo() {
        return getDemoService.getPostsPlainJSON();
    }

}
