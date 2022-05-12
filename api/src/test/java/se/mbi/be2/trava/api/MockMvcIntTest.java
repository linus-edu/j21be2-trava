package se.mbi.be2.trava.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcIntTest {

    // https://www.baeldung.com/spring-boot-testing

    @Autowired
    private MockMvc mockMvc;
    
    private ResultActions performWithLogin(MockHttpServletRequestBuilder builder) throws Exception {
        String token = mockMvc.perform(get("/login"))
                .andReturn().getResponse().getContentAsString();
        return mockMvc.perform(builder.header("Authorization", "Bearer " + token));
    }

    @Test
    public void shouldUseToken() throws Exception {
        performWithLogin(get("/message-worker"))
            .andExpect(content().string(containsString("ok")));
    }
    @Test
    // Example of MockMvc and Hamcrest
    public void shouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print()) // Print result details
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("This is Trava!")));

//        mockMvc.perform(get("/"))
//            .andExpect(content().string(containsString("Hello")));
    }

}