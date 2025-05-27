package de.flowsuite.mailflow.common.dto;

import de.flowsuite.mailflow.common.entity.User;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
public record GenerationRequest(
        User user,
        List<ThreadMessage> messageThread,
        String fromEmailAddress,
        String subject,
        ZonedDateTime receivedAt,
        CategorisationResponse categorisationResponse) {}
