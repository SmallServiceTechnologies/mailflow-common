package de.flowsuite.mailflow.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MessageCategoryDescriptionException extends RuntimeException {

    public MessageCategoryDescriptionException(int minLength) {
        super(
                String.format(
                        "The description must be at least %d characters long to provide sufficient"
                                + " detail.",
                        minLength));
    }
}
