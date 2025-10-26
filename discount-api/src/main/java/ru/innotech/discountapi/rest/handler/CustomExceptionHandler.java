package ru.innotech.discountapi.rest.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.innotech.discountapi.dto.exception.ExceptionResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception exception, WebRequest webRequest) {
        String message = exception.getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, LocalDateTime.now(), webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }
}
