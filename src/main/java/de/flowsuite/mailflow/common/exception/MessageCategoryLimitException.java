package de.flowsuite.mailflow.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MessageCategoryLimitException extends RuntimeException {

    public MessageCategoryLimitException() {
        super("Unable to create category: Maximum number of message categories reached (10).");
    }
}
