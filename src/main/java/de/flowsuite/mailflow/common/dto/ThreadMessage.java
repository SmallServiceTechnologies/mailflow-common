package de.flowsuite.mailflow.common.dto;

import java.util.Date;

public record ThreadMessage(int index, String from, Date receivedAt, String subject, String body) {

    public ThreadMessage(int index, String from, Date receivedAt, String subject, String body) {
        this.index = index;
        this.from = from.trim();
        this.receivedAt = receivedAt;
        this.subject = subject.trim();
        this.body = body.trim();
    }

    @Override
    public String toString() {
        return "Message "
                + index
                + "\n"
                + "From: "
                + from
                + "\n"
                + "Received at: "
                + receivedAt
                + "\n"
                + "Subject: "
                + subject
                + "\n"
                + "Body:\n"
                + body;
    }
}
