package com.agh.groupget.accounts.infrastructure.rest;

import com.agh.groupget.accounts.domain.ErrorResponse;
import com.agh.groupget.accounts.domain.exception.BusinessException;
import com.agh.groupget.accounts.domain.exception.ForbiddenException;
import com.agh.groupget.accounts.domain.exception.RabbitException;
import com.agh.groupget.accounts.domain.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
class ServiceExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.code = String.valueOf(HttpStatus.BAD_REQUEST.value());
        response.message = ex.getMessage();

        LOGGER.info("HTTP 400 - {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.code = String.valueOf(HttpStatus.NOT_FOUND.value());
        response.message = ex.getMessage();

        LOGGER.info("HTTP 404 - {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    ResponseEntity<ErrorResponse> handleUnauthorizedException(ForbiddenException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.code = String.valueOf(HttpStatus.FORBIDDEN.value());
        response.message = ex.getMessage();

        LOGGER.info("HTTP 403 - {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.code = String.valueOf(HttpStatus.BAD_REQUEST.value());
        response.message = "Invalid method argument - " + ex.getName();

        LOGGER.info("HTTP 400 -  Invalid method argument - {} - for request {}", ex.getName(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMediaTypeNotSupportedException.class})
    ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(Exception ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.code = String.valueOf(HttpStatus.BAD_REQUEST.value());
        response.message = "JSON not valid";

        LOGGER.info("HTTP 400 -  Invalid JSON for request {}", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RabbitException.class)
    ResponseEntity<ErrorResponse> handleRabbitException(RabbitException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.code = String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value());
        response.message = ex.getMessage();

        LOGGER.error("HTTP 503 -  Service unavailable for request {}", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
