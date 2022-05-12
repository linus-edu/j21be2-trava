package se.mbi.be2.trava.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.mbi.be2.trava.api.controller.HelloController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HelloController.class)
@Import(se.mbi.be2.trava.api.security.JwtTokenService.class)
public class MockMvcIntTest2 {

    // From https://www.youtube.com/watch?v=pNiRNRgi5Ws

    @Autowired
    private MockMvc mvc;

    @Test
    void getHello() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/hello");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("Hello!", result.getResponse().getContentAsString());
    }

    @Test
    void getHello2() throws Exception {
        // Another way of doing the same thing
        mvc.perform(get("/hello"))
                .andExpect(content().string("Hello!"));
    }

    @Test
    void rootShouldNotBeFound() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is(404));
    }

}
