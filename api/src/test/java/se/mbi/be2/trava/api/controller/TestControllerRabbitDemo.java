package se.mbi.be2.trava.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import se.mbi.be2.trava.api.service.RabbitWorkerService;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerRabbitDemo {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RabbitWorkerService rabbitWorkerService;

    @Test
    void messageWorker() throws Exception {
        reset(rabbitWorkerService);

        mvc.perform(get("/api/demo/rabbitmq"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ok")));

         verify(rabbitWorkerService, times(1)).sendJob();
    }

}