package de.flowsuite.mailflow.common.entity;

import static de.flowsuite.mailflow.common.util.Util.BERLIN_ZONE;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "message_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @NotNull private Long userId;

    @Column(updatable = false)
    @NotNull private Long customerId;

    private boolean replied;
    private boolean functionCall;
    @NotBlank private String category;
    private String language;

    @Column(name = "from_email_address_encrypted")
    private String fromEmailAddress;

    private String subject;
    @NotNull private ZonedDateTime receivedAt;
    @NotNull private ZonedDateTime processedAt;
    @NotNull private Integer processingTimeInSeconds;
    private String categorisationLlmUsed;
    private Integer categorisationInputTokens;
    private Integer categorisationOutputTokens;
    private Integer categorisationTotalTokens;
    private String llmUsed;
    private Integer inputTokens;
    private Integer outputTokens;
    private Integer totalTokens;
    @NotBlank private String token;
    @NotNull private ZonedDateTime tokenExpiresAt;

    @PrePersist
    @PreUpdate
    private void setTimestampsToBerlin() {
        if (receivedAt != null) {
            receivedAt = receivedAt.withZoneSameInstant(BERLIN_ZONE);
        }
        if (processedAt != null) {
            processedAt = processedAt.withZoneSameInstant(BERLIN_ZONE);
        }
        if (tokenExpiresAt != null) {
            tokenExpiresAt = tokenExpiresAt.withZoneSameInstant(BERLIN_ZONE);
        }
    }
}
