package de.flowsuite.mailflow.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MessageCategoryDescriptionException extends RuntimeException {

    public MessageCategoryDescriptionException() {
        super("The description must be at least 100 characters long to provide sufficient detail.");
    }
}
