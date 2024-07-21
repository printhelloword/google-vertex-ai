package com.google.vertex.dto.chatagent.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiagnosticInfo {
    @JsonProperty("Session Id")
    public String sessionId;
    @JsonProperty("Response Id")
    public String responseId;
}
