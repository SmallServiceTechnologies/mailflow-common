package de.flowsuite.mailflow.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public record CreateMessageLogEntryRequest(
        @NotNull Long userId,
        @NotNull Long customerId,
        boolean isReplied,
        @NotBlank String category,
        String language,
        String fromEmailAddress,
        String subject,
        @NotNull ZonedDateTime receivedAt,
        @NotNull ZonedDateTime processedAt,
        @NotNull Integer processingTimeInSeconds,
        String categorisationLlmUsed,
        Integer categorisationInputTokens,
        Integer categorisationOutputTokens,
        Integer categorisationTotalTokens,
        String llmUsed,
        Integer inputTokens,
        Integer outputTokens,
        Integer totalTokens) {}
