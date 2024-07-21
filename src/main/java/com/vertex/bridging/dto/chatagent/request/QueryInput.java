package com.vertex.bridging.dto.chatagent.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryInput {
    public Text text;
    public String languageCode;
}
