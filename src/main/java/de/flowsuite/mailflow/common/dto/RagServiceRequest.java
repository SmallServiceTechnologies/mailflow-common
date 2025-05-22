package de.flowsuite.mailflow.common.dto;

import de.flowsuite.mailflow.common.entity.Customer;
import de.flowsuite.mailflow.common.entity.User;
import lombok.Builder;

@Builder
public record RagServiceRequest(User user, Customer customer, String messageThread) {}
