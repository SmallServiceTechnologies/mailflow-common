package de.flowsuite.mailflow.common.dto;

import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public record UpdateCustomerCrawlStatusRequest(@NotNull Long id, @NotNull ZonedDateTime lastCrawlAt, @NotNull ZonedDateTime nextCrawlAt) {}
