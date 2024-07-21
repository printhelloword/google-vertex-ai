package com.google.vertex.service;

import com.google.vertex.config.GoogleConsoleConfig;
import com.google.vertex.dto.chatagent.request.ChatAgentRequest;
import com.google.vertex.dto.chatagent.request.QueryInput;
import com.google.vertex.dto.chatagent.request.QueryParams;
import com.google.vertex.dto.chatagent.request.Text;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class ChatAgentService {
    public static final String TIMEZONE = "Asia/Bangkok";
    private final GoogleConsoleConfig googleConsoleConfig;
    private final GoogleApiRestClient googleApiRestClient;

    public String getDialogFlowResponse(String input, String language) throws IOException {
        ChatAgentRequest chatAgentRequest = getChatAgentRequest(input, language);
        String url = buildUrl();
        log.info("generated service url: {}", url);
        return googleApiRestClient.makeApiCall(url, HttpMethod.POST, chatAgentRequest);
    }

    private static ChatAgentRequest getChatAgentRequest(String input, String language) {
        Text text = new Text(input);
        QueryInput queryInput = new QueryInput(text, language);
        QueryParams queryParams = new QueryParams(TIMEZONE);
        return new ChatAgentRequest(queryInput, queryParams);
    }

    private String buildUrl() {
        return "https://" + googleConsoleConfig.getRegionId() + "-dialogflow.googleapis.com/v3/projects/" + googleConsoleConfig.getProjectId() + "/locations/" + googleConsoleConfig.getRegionId() + "/agents/" + googleConsoleConfig.getAgentId() + "/sessions/" + UUID.randomUUID().toString().substring(0, 8) + ":detectIntent";
    }

}
