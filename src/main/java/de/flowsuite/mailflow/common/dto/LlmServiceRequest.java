package de.flowsuite.mailflow.common.dto;

import de.flowsuite.mailflow.common.entity.MessageCategory;
import de.flowsuite.mailflow.common.entity.User;

import java.util.List;

public record LlmServiceRequest (User user, String text, List<MessageCategory> categories) {}
