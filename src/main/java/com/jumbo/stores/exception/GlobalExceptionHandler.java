package com.jumbo.stores.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleRecordNotFoundException(RecordNotFoundException e, WebRequest webRequest) {
        String path = webRequest.getDescription(false).replace("uri=", "");
        logger.error(e.getMessage());
        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ErrorResponse(status.value(), status.getReasonPhrase(), e.getMessage(), path);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse MethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest webRequest) {
        String path = webRequest.getDescription(false).replace("uri=", "");
        logger.error(e.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ValidationErrorDetails> errorDetails = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails(fieldError.getField(), fieldError.getDefaultMessage());
            errorDetails.add(validationErrorDetails);
        }

        return new ErrorResponse(status.value(), status.getReasonPhrase(), e.getBody().getDetail(), path, errorDetails);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleUserAlreadyExistsException(UserAlreadyExistsException e, WebRequest webRequest) {
        String path = webRequest.getDescription(false).replace("uri=", "");
        logger.error(e.getMessage());
        HttpStatus status = HttpStatus.CONFLICT;

        return new ErrorResponse(status.value(), status.getReasonPhrase(), e.getMessage(), path);
    }
}
