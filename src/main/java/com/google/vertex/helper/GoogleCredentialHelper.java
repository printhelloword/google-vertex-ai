package com.google.vertex.helper;

import com.google.auth.oauth2.GoogleCredentials;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

@Component
public class GoogleCredentialHelper {
    private static final Logger log = LoggerFactory.getLogger(GoogleCredentialHelper.class);
    private String token;
    private Date tokenExpiryTime;

    private final Resource googleConsoleCred;

    @Getter
    private GoogleCredentials credential;


    public GoogleCredentialHelper(@Value("${google.console.cred}") Resource cred) throws IOException {
        this.googleConsoleCred = cred;
        refreshToken();
    }

    public String getToken() throws IOException {
        if (shouldRefreshToken()) {
            log.info("Token needs to be refreshed");
            refreshToken();
        }
        log.info("token {}....", token.length() > 100 ? token.substring(0, 100) : token);
        return token;
    }

    private boolean shouldRefreshToken() {
        log.info("tokenExpiryTime {}", tokenExpiryTime);
        return tokenExpiryTime == null || (tokenExpiryTime.getTime() - System.currentTimeMillis()) <= 300 * 1000;
    }

    private void refreshToken() throws IOException {
        log.info("refreshing token");
        InputStream resource = googleConsoleCred.getInputStream();
        credential = GoogleCredentials.fromStream(resource).createScoped(Arrays.asList(
                "https://www.googleapis.com/auth/devstorage.full_control", "https://www.googleapis.com/auth/dialogflow"));

        credential.refreshIfExpired();
        token = credential.getAccessToken().getTokenValue();
        tokenExpiryTime = credential.getAccessToken().getExpirationTime();
    }
}
