package de.flowsuite.mailflow.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Settings {

    @Id @NotNull private Long userId;

    @Column(updatable = false)
    @NotNull private Long customerId;

    private boolean executionEnabled;
    private boolean autoReplyEnabled;
    private boolean responseRatingEnabled;
    private boolean moveToManualReviewEnabled;

    @JsonIgnore private String mailboxPasswordHash;

    @Column(name = "mailbox_password_encrypted")
    @NotBlank private String mailboxPassword;

    private String imapHost;
    private String smtpHost;
    private Integer imapPort;
    private Integer smtpPort;
}
