package de.flowsuite.mailflow.common.util;

import de.flowsuite.mailflow.common.entity.Settings;
import de.flowsuite.mailflow.common.exception.InvalidEmailAddressException;
import de.flowsuite.mailflow.common.exception.InvalidPortsException;
import de.flowsuite.mailflow.common.exception.InvalidSettingsException;
import de.flowsuite.mailflow.common.exception.InvalidUrlException;
import jakarta.validation.Valid;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.time.ZoneId;
import java.util.Base64;
import java.util.List;

public class Util {

    public static final ZoneId BERLIN_ZONE = ZoneId.of("Europe/Berlin");
    private static final List<Integer> VALID_IMAP_PORTS = List.of(993);
    private static final List<Integer> VALID_SMTP_PORTS = List.of(465, 587, 2525);

    public static void validateEmailAddress(String emailAddress) {
        if (emailAddress == null
                || emailAddress.isBlank()
                || !(emailAddress.contains("@") && emailAddress.contains("."))) {
            throw new InvalidEmailAddressException();
        }

        String[] emailAddressParts = emailAddress.split("@");
        if (emailAddressParts.length != 2) {
            throw new InvalidEmailAddressException();
        }

        String localPart = emailAddressParts[0];
        if (localPart.isBlank()) {
            throw new InvalidEmailAddressException();
        }

        String domain = emailAddressParts[1];
        String[] domainParts = domain.split("\\.");

        if (domainParts.length == 1) {
            throw new InvalidEmailAddressException();
        }

        for (String part : domainParts) {
            if (part.isBlank()) {
                throw new InvalidEmailAddressException();
            }
        }

        String secondLevelDomain = domainParts[0];
        String topLevelDomain = domainParts[1];

        if (secondLevelDomain.isBlank() || topLevelDomain.isBlank()) {
            throw new InvalidEmailAddressException();
        }
    }

    public static void validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new InvalidUrlException("URL must not be blank.");
        }

        URL parsedUrl;
        try {
            parsedUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new InvalidUrlException("The provided URL is invalid: " + e.getMessage());
        }

        if (!"https".equalsIgnoreCase(parsedUrl.getProtocol())) {
            throw new InvalidUrlException("Only https URLs are allowed.");
        }
    }

    public static void validateMailboxSettings(String imapHost, String smtpHost, Integer imapPort, Integer smtpPort) {
        if (imapHost == null
                || smtpHost == null
                || imapPort == null
                || smtpPort == null
                || imapHost.isBlank()
                || smtpHost.isBlank()) {
            throw new InvalidSettingsException();
        }

        if (!VALID_IMAP_PORTS.contains(imapPort)
                || !VALID_SMTP_PORTS.contains(smtpPort)) {
            throw new InvalidPortsException(VALID_IMAP_PORTS.toString(), VALID_SMTP_PORTS.toString());
        }
    }

    public static String generateRandomUrlSafeToken() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
