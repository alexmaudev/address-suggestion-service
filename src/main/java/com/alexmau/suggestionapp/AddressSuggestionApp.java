package com.alexmau.suggestionapp;

import com.alexmau.suggestionapp.client.DadataClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AddressSuggestionApp {

    public static void main(String[] args) {
        SpringApplication.run(AddressSuggestionApp.class, args);
    }

    @Bean
    public DadataClient dadataClient() {
        return new DadataClient();
    }
}
