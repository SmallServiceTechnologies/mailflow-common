package de.flowsuite.mailflow.common.dto;

import jakarta.validation.constraints.NotBlank;

public record LlmResponse(
        @NotBlank String text,
        @NotBlank String llmUsed,
        int inputTokens,
        int outputTokens,
        int totalTokens) {}
