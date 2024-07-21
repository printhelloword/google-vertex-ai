package com.vertex.bridging.config;

import com.vertex.bridging.helper.GoogleTokenHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public GoogleTokenHelper googleTokenHelper() throws IOException {
        log.info("Init Singleton GoogleTokenHelper");
        return new GoogleTokenHelper();
    }

}
