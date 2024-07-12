package com.vertex.bridging.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("google.console")
public class GoogleConsoleConfig {
    private String bucketName;
    private String projectId;
    private String dataStorageId;
}