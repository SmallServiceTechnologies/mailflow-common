package de.flowsuite.mailflow.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MessageCategoryLimitException extends RuntimeException {

    public MessageCategoryLimitException(int maxCategoriesPerCustomer) {
        super(String.format("Unable to create category: Maximum number of message categories reached (%d).", maxCategoriesPerCustomer));
    }
}
