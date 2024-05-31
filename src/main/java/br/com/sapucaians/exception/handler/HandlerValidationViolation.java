package br.com.sapucaians.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class HandlerValidationViolation {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(BindException ex) {
        Map<String, String> errorsMap = new HashMap<>();
        List<FieldError> errors = ex.getFieldErrors();

        for (FieldError error : errors) {
            String field = error.getField();
            String message = error.getDefaultMessage();
            errorsMap.put(field, message);
        }

        return errorsMap;
    }
}
