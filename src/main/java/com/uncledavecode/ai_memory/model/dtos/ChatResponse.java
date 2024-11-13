package com.uncledavecode.ai_memory.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatResponse(
        @JsonProperty("response")
        String response
) {
}
