package com.vertex.bridging.dto.chatagent.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public class QueryResult {
    public String text;
    public String languageCode;
    public ArrayList<ResponseMessage> responseMessages;
    public int intentDetectionConfidence;
    public DiagnosticInfo diagnosticInfo;
    public Match match;
    public AdvancedSettings advancedSettings;
}
