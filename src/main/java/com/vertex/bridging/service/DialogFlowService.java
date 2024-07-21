package com.vertex.bridging.service;

import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.vertex.bridging.config.GoogleConsoleConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class DialogFlowService {

    private final GoogleConsoleConfig googleConsoleConfig;
    private final ResourceLoader resourceLoader;

    public String detectIntentTexts(String text)
            throws IOException, ApiException {
        Resource resource = resourceLoader.getResource("classpath:service-account.json");

        GoogleCredentials.fromStream(new FileInputStream(resource.getFile()))
                .createScoped("https://www.googleapis.com/auth/cloud-platform");

        try (SessionsClient sessionsClient = SessionsClient.create()) {
            SessionName session = SessionName.of(googleConsoleConfig.getProjectId(), UUID.randomUUID().toString().substring(0, 8));
            System.out.println("Session Path: " + session.toString());

            // Detect intents for each text input
            // Set the text (hello) and language code (en-US) for the query
            TextInput.Builder textInput =
                    TextInput.newBuilder().setText(text).setLanguageCode("en");

            // Build the query with the TextInput
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

            // Performs the detect intent request
            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

            // Display the query result
            QueryResult queryResult = response.getQueryResult();

            System.out.println("====================");
            System.out.format("Query Text: '%s'\n", queryResult.getQueryText());
            System.out.format(
                    "Detected Intent: %s (confidence: %f)\n",
                    queryResult.getIntent().getDisplayName(), queryResult.getIntentDetectionConfidence());
            System.out.format(
                    "Fulfillment Text: '%s'\n",
                    queryResult.getFulfillmentMessagesCount() > 0
                            ? queryResult.getFulfillmentMessages(0).getText()
                            : "Triggered Default Fallback Intent");

            return queryResult.getQueryText();
        }
    }
}
