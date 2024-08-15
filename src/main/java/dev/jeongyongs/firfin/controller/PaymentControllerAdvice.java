package dev.jeongyongs.firfin.controller;

import dev.jeongyongs.firfin.dto.ErrorResponse;
import dev.jeongyongs.firfin.exception.AbstractException;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentControllerAdvice {

    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRequestException(AbstractException exception) {
        ErrorResponse body = new ErrorResponse(exception.getErrorCode(), exception.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(exception.getHttpStatus())
                             .body(body);
    }
}
