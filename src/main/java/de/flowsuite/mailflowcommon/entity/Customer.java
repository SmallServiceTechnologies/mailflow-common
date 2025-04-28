package de.flowsuite.mailflowcommon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonIgnore private String sourceOfContact;
    private String websiteUrl;
    private String privacyPolicyUrl;
    private String ctaUrl;

    @Column(updatable = false)
    @NotBlank private String registrationToken;

    private boolean isTestVersion;
}
