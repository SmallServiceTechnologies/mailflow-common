package de.flowsuite.mailflow.common.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record RagServiceRequest(long userId, long customerId, List<ThreadMessage> messageThread) {}
