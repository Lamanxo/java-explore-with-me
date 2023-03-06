package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(final NotFoundException e) {
        log.warn("Not found {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(final BadRequestException e) {
        log.warn("Bad request {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflictData(final ConflictException e) {
        log.warn("Conflict with data {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflictData(final DataIntegrityViolationException e) {
        log.warn("Conflict with data {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflictData(final HttpMessageNotReadableException e) {
        log.warn("Conflict with data {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse endpointParamHandler(final MissingServletRequestParameterException e) {
        log.warn("Bad request {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse endpointBodyHandler(final MethodArgumentNotValidException e) {
        log.warn("Bad request {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse sqlUniqueFieldHandler(final ConstraintViolationException e) {
        log.warn("Bad request {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse sqlUniqueFieldHandler(final IllegalArgumentException e) {
        log.warn("Bad request {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse elseExceptions(final Throwable e) {
        log.warn("Internal server error {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}
