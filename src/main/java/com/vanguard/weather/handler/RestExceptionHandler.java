package com.vanguard.weather.handler;

import com.vanguard.weather.exceptions.LimitRequestException;
import com.vanguard.weather.exceptions.MissingApiKeyException;
import com.vanguard.weather.utils.StandardResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({LimitRequestException.class})
    protected ResponseEntity<StandardResponse<String>> handleLimitRequest(LimitRequestException ex) {
        return new ResponseEntity<>(new StandardResponse<>(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MissingApiKeyException.class)
    protected ResponseEntity<StandardResponse<String>> handleMissingApiKey(MissingApiKeyException ex) {
        return new ResponseEntity<>(new StandardResponse<>(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<StandardResponse<String>> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(new StandardResponse<>(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<StandardResponse<String>> handleUnknownException(Throwable ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new StandardResponse<>("Internal server error" + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}