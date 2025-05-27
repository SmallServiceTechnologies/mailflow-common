package de.flowsuite.mailflow.common.dto;

import de.flowsuite.mailflow.common.entity.MessageCategory;
import de.flowsuite.mailflow.common.entity.User;

import lombok.Builder;

import java.util.List;

@Builder
public record CategorisationRequest(User user, String text, List<MessageCategory> categories) {}
