package com.tomazbr9.tomtech.exception.handle;

import com.tomazbr9.tomtech.exception.BusinessRuleException;
import com.tomazbr9.tomtech.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRuleException(
            BusinessRuleException exception, HttpServletRequest request
    ){
        return buildException(exception, request, HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception, HttpServletRequest request
    ){
        return buildException(exception, request, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> buildException(
            Exception exception,
            HttpServletRequest request,
            HttpStatus status
    ){
        ErrorResponse error = new ErrorResponse(
                status.value(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(error);
    }
}
