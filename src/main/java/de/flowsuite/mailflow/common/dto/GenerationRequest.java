package de.flowsuite.mailflow.common.dto;

import de.flowsuite.mailflow.common.entity.User;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record GenerationRequest(
        User user,
        String text,
        String fromEmailAddress,
        String subject,
        ZonedDateTime receivedAt,
        CategorisationResponse categorisationResponse) {}
