package com.tomazbr9.tomtech.exception.handle;

import com.tomazbr9.tomtech.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRuleException(
            BusinessRuleException exception, HttpServletRequest request
    ){
        log.warn("Esta ação fere as regras de negocio", exception);
        return buildException(exception, request, HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception, HttpServletRequest request
    ){
        log.warn("Recurso não encontrado", exception);
        return buildException(exception, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request){
        log.warn("Acesso negado", exception);
        return buildException(exception, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ImageUploadFailedException.class)
    public ResponseEntity<ErrorResponse> handleImageUploadFailedException(ImageUploadFailedException exception, HttpServletRequest request){
        log.error("Falha ao fazer upload do arquivo", exception);
        return buildException(exception, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<ErrorResponse> handleInvalidImageException(InvalidImageException exception, HttpServletRequest request){
        log.warn("A imagem enviada é inválida", exception);
        return buildException(exception, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception, HttpServletRequest request){

        log.error(
                "Erro interno não tratado. path={}",
                request.getRequestURI(),
                exception
        );


        return buildException(
                new RuntimeException("Erro interno do servidor"),
                request,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
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
