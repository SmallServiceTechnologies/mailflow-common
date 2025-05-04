package de.flowsuite.mailflow.common;

import de.flowsuite.mailflow.common.exception.TrimValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

@RestControllerAdvice
public class TrimRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(TrimRequestBodyAdvice.class);

    @Override
    public boolean supports(
            MethodParameter methodParameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(
            Object body,
            HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        validateStringFields(body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    private void validateStringFields(Object object) {
        if (object == null) return;

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(object);
                    if (value == null) {
                        LOG.debug("Field {} is null.", field.getName());
                        continue;
                    }
                    String trimmedValue = value.trim();
                    if (!trimmedValue.equals(value)) {
                        throw new TrimValidationException(field.getName());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
