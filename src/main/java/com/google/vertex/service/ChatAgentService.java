package com.google.vertex.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import com.google.vertex.config.GoogleConsoleConfig;
import com.google.vertex.dto.chatagent.request.ChatAgentRequest;
import com.google.vertex.helper.GoogleTokenHelper;
import com.google.vertex.dto.chatagent.request.QueryInput;
import com.google.vertex.dto.chatagent.request.QueryParams;
import com.google.vertex.dto.chatagent.request.Text;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class ChatAgentService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final GoogleConsoleConfig googleConsoleConfig;
    private final Gson gson;
    /*private GoogleCredentials googleCredentials;*/
    private final GoogleTokenHelper googleTokenHelper;

    public String getDialogFlowResponse(String input, String language) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + googleTokenHelper.getToken());

        Text text = new Text(input);
        QueryInput queryInput = new QueryInput(text, language);
        QueryParams queryParams = new QueryParams("America/Los_Angeles");
        ChatAgentRequest chatAgentRequest = new ChatAgentRequest(queryInput, queryParams);

        String finalUrl = getDialogflowChatUrl();
        log.info("generated url: {}", finalUrl);
        HttpEntity<ChatAgentRequest> requestEntity = new HttpEntity<>(chatAgentRequest, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.POST, requestEntity, String.class);
        /*ChatAgentResponse responseBody = response.getBody();
        if (DialogFlowMatchType.NO_MATCH.name().equalsIgnoreCase(responseBody.getQueryResult().getMatch().getMatchType())) {
            log.info("Query {} has NO MATCH", text);
            return responseBody.getQueryResult().getMatch().getMatchType();
        }*/
        return response.getBody();
    }

    private String getAccessToken() throws IOException {
        InputStream resource = new ClassPathResource(
                "service-account.json").getInputStream();
        GoogleCredentials credential = GoogleCredentials.fromStream(resource).createScoped(Arrays.asList(
        "https://www.googleapis.com/auth/devstorage.full_control", "https://www.googleapis.com/auth/dialogflow"));
        return credential.getAccessToken() != null ?
                credential.getAccessToken().getTokenValue() :
                credential.refreshAccessToken().getTokenValue();
    }

    private String getDialogflowChatUrl() {
        return "https://" + googleConsoleConfig.getRegionId() + "-dialogflow.googleapis.com/v3/projects/" + googleConsoleConfig.getProjectId() + "/locations/" + googleConsoleConfig.getRegionId() + "/agents/" + googleConsoleConfig.getAgentId() + "/sessions/" + UUID.randomUUID().toString().substring(0, 8) + ":detectIntent";
    }

}
