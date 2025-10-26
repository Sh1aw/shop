package ru.innotech.discountapi.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private LocalDateTime dateTime;
    private String details;
}
