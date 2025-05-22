package de.flowsuite.mailflow.common.entity;

import static de.flowsuite.mailflow.common.util.Util.BERLIN_ZONE;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String company;
    @NotBlank private String street;
    @NotBlank private String houseNumber;
    @NotBlank private String postalCode;
    @NotBlank private String city;
    @Email @NotBlank String billingEmailAddress;

    @Column(name = "openai_api_key_encrypted", updatable = false)
    @NotBlank private String openaiApiKey;

    private String systemPrompt;
    private String messagePrompt;

    @JsonIgnore private String sourceOfContact;
    private String websiteUrl;
    private String privacyPolicyUrl;
    private String ctaUrl;

    @Column(updatable = false)
    @NotBlank private String registrationToken;

    private boolean testVersion;
    private String ionosUsername;

    @Column(name = "ionos_password_encrypted")
    private String ionosPassword;

    @Min(1) @Max(14) private int crawlFrequencyInDays;

    private ZonedDateTime lastCrawlAt;
    private ZonedDateTime nextCrawlAt;

    @NotBlank private String defaultImapHost;
    @NotBlank private String defaultSmtpHost;
    @NotNull private Integer defaultImapPort;
    @NotNull private Integer defaultSmtpPort;

    @PrePersist
    @PreUpdate
    private void setTimestampsToBerlin() {
        if (lastCrawlAt != null) {
            lastCrawlAt = lastCrawlAt.withZoneSameInstant(BERLIN_ZONE);
        }
        if (nextCrawlAt != null) {
            nextCrawlAt = nextCrawlAt.withZoneSameInstant(BERLIN_ZONE);
        }
    }
}
