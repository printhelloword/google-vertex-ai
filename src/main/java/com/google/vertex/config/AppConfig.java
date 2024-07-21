package com.google.vertex.config;

import com.google.vertex.helper.GoogleTokenHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Log4j2
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        log.info("Init Singleton RestTemplate");
        return new RestTemplate();
    }

    @Bean
    public GoogleTokenHelper googleTokenHelper(@Value("${google.console.cred}") Resource cred) throws IOException {
        log.info("Init Singleton GoogleTokenHelper");
        return new GoogleTokenHelper(cred);
    }

}
