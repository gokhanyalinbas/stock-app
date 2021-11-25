package com.payconiq.stockapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;


@RestControllerAdvice
public class StockExceptionHandler {

    @ExceptionHandler(StockNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ExceptionResponse handleStockNotFoundException(Exception ex) {
        System.err.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        return new ExceptionResponse("NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleAllOtherExceptions(Exception ex) {
        System.err.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        return new ExceptionResponse("FAILED", ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException ex) {
        System.err.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        var violations = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("|"));
        return new ExceptionResponse("validation violation", violations);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        System.err.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        var violations = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("|"));
        return new ExceptionResponse("validation violation", violations);
    }

}
