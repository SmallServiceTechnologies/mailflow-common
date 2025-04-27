package de.flowsuite.mailflowcommon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import static de.flowsuite.mailflowcommon.util.Util.BERLIN_ZONE;

@Entity
@Table(name = "response_ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseRating {

    @Id @NotNull private Long messageLogId;

    @Column(updatable = false)
    @NotNull private Long customerId;

    @Column(updatable = false)
    @NotNull private Long userId;

    boolean isSatisfied;

    @Min(0) @Max(5) private int rating;

    private String feedback;
    @NotNull private ZonedDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now(BERLIN_ZONE);
    }
}
