package com.vertex.bridging.dto.chatagent.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatAgentResponse {
    public String responseId;
    public QueryResult queryResult;
    public String responseType;
}
