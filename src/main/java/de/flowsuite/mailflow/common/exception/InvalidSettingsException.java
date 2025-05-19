package de.flowsuite.mailflow.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidSettingsException extends RuntimeException {

    public InvalidSettingsException() {
        super("Mailbox host and port settings are missing or invalid");
    }

    public InvalidSettingsException(String message) {
        super(message);
    }
}
