package com.google.vertex.service;

import com.google.vertex.helper.GoogleTokenHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
@Service
public class GoogleApiRestClient {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    private final RestTemplate restTemplate;
    private final GoogleTokenHelper googleTokenHelper;

    public String makeApiCall(String url, HttpMethod method, Object requestBody) throws IOException {
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        requestEntity.getHeaders().forEach((key, value) -> headers.add(key, value.toString()));

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, method, requestEntity, String.class);
        } catch (Exception e) {
            log.info("Error making API call: {}", e);
        }

        return response.getBody();
    }

    private HttpHeaders getHttpHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTHORIZATION, BEARER + googleTokenHelper.getToken());
        return headers;
    }


}
