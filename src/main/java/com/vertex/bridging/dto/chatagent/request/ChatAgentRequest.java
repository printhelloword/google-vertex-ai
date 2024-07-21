package com.vertex.bridging.dto.chatagent.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatAgentRequest {
    public QueryInput queryInput;
    public QueryParams queryParams;
}
