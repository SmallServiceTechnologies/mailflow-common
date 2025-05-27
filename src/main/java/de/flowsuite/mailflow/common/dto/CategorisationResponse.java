package de.flowsuite.mailflow.common.dto;

import de.flowsuite.mailflow.common.entity.MessageCategory;

import lombok.Builder;

@Builder
public record CategorisationResponse(
        MessageCategory messageCategory,
        String llmUsed,
        Integer inputTokens,
        Integer outputTokens,
        Integer totalTokens) {}
