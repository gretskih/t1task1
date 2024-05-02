package ru.t1.aophome.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig implements AsyncConfigurer {

    @Bean
    public ExecutorService myExecutorService() {
        return Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
    }

    @Bean
    public ModelMapper myModelMapper() {
        return new ModelMapper();
    }
}
