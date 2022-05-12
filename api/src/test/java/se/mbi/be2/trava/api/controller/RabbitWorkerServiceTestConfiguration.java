package se.mbi.be2.trava.api.controller;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import se.mbi.be2.trava.api.service.RabbitService;
import se.mbi.be2.trava.api.service.RabbitWorkerService;

@Profile("test")
@Configuration
public class RabbitWorkerServiceTestConfiguration {
    @Bean
    @Primary
    public RabbitWorkerService rabbitWorkerService() throws Exception {
        var mocked = Mockito.mock(RabbitWorkerService.class);
        // when(mocked.sendJob(0)).thenReturn(0);
        return mocked;
    }

    @Bean
    @Primary
    public RabbitService rabbitService() throws Exception {
        var mocked = Mockito.mock(RabbitService.class);
        return mocked;
    }
}