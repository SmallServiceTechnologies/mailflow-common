package de.flowsuite.mailflow.common.util;

import de.flowsuite.mailflow.common.dto.ThreadMessage;
import de.flowsuite.mailflow.common.exception.InvalidEmailAddressException;
import de.flowsuite.mailflow.common.exception.InvalidPortsException;
import de.flowsuite.mailflow.common.exception.InvalidSettingsException;
import de.flowsuite.mailflow.common.exception.InvalidUrlException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class Util {

    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

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

    public static void validateMailboxSettings(
            String imapHost, String smtpHost, Integer imapPort, Integer smtpPort) {
        if (imapHost == null
                || smtpHost == null
                || imapPort == null
                || smtpPort == null
                || imapHost.isBlank()
                || smtpHost.isBlank()) {
            throw new InvalidSettingsException();
        }

        if (!VALID_IMAP_PORTS.contains(imapPort) || !VALID_SMTP_PORTS.contains(smtpPort)) {
            throw new InvalidPortsException(
                    VALID_IMAP_PORTS.toString(), VALID_SMTP_PORTS.toString());
        }
    }

    public static String generateRandomUrlSafeToken() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public static String buildThreadBody(
            List<ThreadMessage> messageThread,
            boolean format,
            boolean truncate,
            Integer maxLength) {
        // Reverse the list so we can iterate from newest to oldest
        List<ThreadMessage> reversedMessages = new ArrayList<>(messageThread);
        Collections.reverse(reversedMessages);

        StringBuilder threadBuilder = new StringBuilder();
        List<String> retainedMessages = new ArrayList<>();
        int currentLength = 0;

        for (int i = 0; i < reversedMessages.size(); i++) {
            ThreadMessage message = reversedMessages.get(i);

            String text;
            if (format) {
                text = message.toString();
            } else {
                String subject = message.subject();
                if (subject != null && !subject.isBlank() && !subject.endsWith(".")) {
                    subject = subject + ".";
                }
                text = subject + " " + message.body();
            }

            boolean isMostRecent = (i == 0);
            int messageLength =
                    text.length() + (isMostRecent ? "## Most recent message\n".length() : 0);

            LOG.debug("Current thread body length: {}", currentLength + messageLength);

            if (truncate && currentLength + messageLength > maxLength) {
                LOG.warn("Skipping older messages due to input size limit...");
                break;
            }

            String formattedMessage = text;
            if (format) {
                formattedMessage = isMostRecent ? "## Most recent message\n" + text : text;
            }

            // Prepend the list that it is ordered oldest to newest
            retainedMessages.add(0, formattedMessage);
            currentLength += messageLength;
        }

        for (String msg : retainedMessages) {
            threadBuilder.append(msg).append("\n\n");
        }

        return threadBuilder.toString();
    }
}
