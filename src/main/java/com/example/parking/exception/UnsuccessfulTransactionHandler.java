package com.example.parking.exception;

import com.example.parking.model.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class UnsuccessfulTransactionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnsuccessfulTransactionException.class)
    public ErrorInfo unsuccessfulTransactionExceptionHandler
            (HttpServletRequest request, UnsuccessfulTransactionException exception) {
        String url = request.getRequestURL().toString();
        String message = exception.getLocalizedMessage();

        return ErrorInfo.builder()
                .url(url)
                .message(message)
                .build();
    }
}