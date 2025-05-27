package de.flowsuite.mailflow.common.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record RagServiceResponse(List<String> relevantSegments, List<String> relevantMetadata) {}
