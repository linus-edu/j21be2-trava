package se.mbi.be2.trava.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtAuthIntTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {

        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/api/users",
            HttpMethod.GET, null, Void.class).getStatusCodeValue()).isEqualTo(403);

        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/api/demo/hello",
                HttpMethod.GET, null, Void.class).getStatusCodeValue()).isEqualTo(200);
    }

    record RequestTokenDto(String username, String password) {}

    private HttpEntity getValidToken() {
        var requestTokenDto = new RequestTokenDto("user", "pass");
        String validToken = restTemplate.postForObject("http://localhost:" + port + "/api/accesstoken", requestTokenDto, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + validToken);
        HttpEntity httpEntity = new HttpEntity(headers);
        return httpEntity;
    }

    @Test
    public void requestWithValidToken() throws Exception {
        HttpEntity httpEntity = getValidToken();
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/api/users",
                HttpMethod.GET, httpEntity, Void.class).getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void requestWithJwtWrongKey() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjUyMjI1MjQzLCJleHAiOjE2NTIyNDMyNDN9.sjGQASrZpYkrDQxqyAm5BOAfcld_Lcnwwi20iksko7-rU5324ZHzuQSMo8po_qIQPGGxb_4y8lM7B-diIK0XYQ");
        HttpEntity httpEntity = new HttpEntity(headers);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/api/users",
                HttpMethod.GET, httpEntity, Void.class).getStatusCodeValue()).isEqualTo(403);
    }

}
