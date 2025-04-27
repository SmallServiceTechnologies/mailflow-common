package de.flowsuite.mailflowcommon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IdConflictException extends RuntimeException {

    public IdConflictException() {
        super("The provided Id is invalid or does not match the expected Id.");
    }
}
