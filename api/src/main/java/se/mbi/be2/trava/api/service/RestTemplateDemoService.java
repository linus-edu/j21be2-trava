package se.mbi.be2.trava.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateDemoService {

    // https://attacomsian.com/blog/http-requests-resttemplate-spring-boot

    private final RestTemplate restTemplate;

    @Value("${api_base_url:https://jsonplaceholder.typicode.com/posts}")
    private String apiBaseUrl;

    RestTemplateDemoService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON() {
        return this.restTemplate.getForObject(apiBaseUrl, String.class);
    }

}
