package de.flowsuite.mailflow.common.constant;

import jakarta.validation.constraints.NotBlank;

public record Message(@NotBlank String message) {
    // spotless:off
    public static final String CREATE_USER_MSG =
            "Your account has been created. Please check your inbox to enable your account.";
    public static final String ENABLE_USER_MSG =
            "Your account has been enabled.";
    public static final String REQUEST_PASSWORD_RESET_MSG =
            "A password reset link will be sent shortly.";
    public static final String COMPLETE_PASSWORD_RESET_MSG =
            "Your password has been updated successfully.";
    public static final String PASSWORDS_DO_NOT_MATCH_MSG =
            "The passwords do not match.";
    public static final String PASSWORD_TOO_SHORT_MSG =
            "The password must be at least %d characters long.";
    public static final String MISSING_CASE_MSG =
            "The password must contain at least one uppercase and one lowercase letter.";
    public static final String MISSING_DIGIT_MSG =
            "The password must contain at least one digit.";
    public static final String MISSING_SPECIAL_CHARACTER_MSG =
            "The password must contain at least one special character.";
    public static final String RESPONSE_RATING_EXPIRED_MSG =
            "This message is no longer eligible for rating. Feedback can only be submitted within %s days of the interaction.";
    public static final String DOUBLE_OPT_IN_EMAIL_SUBJECT =
            "Please confirm your registration 🤝";
    public static final String REGISTRATION_EXPIRED_SUBJECT =
            "Your registration has expired ⏳";
    public static final String RESET_PASSWORD_EMAIL_SUBJECT =
            "Reset your password 🔐";
    public static final String RESET_PASSWORD_EXPIRED_EMAIL_SUBJECT =
            "Your password reset link has expired ⏳";
    public static final String WELCOME_EMAIL_SUBJECT =
            "Welcome to mailflow! 🥳";
    // spotless:on
}
