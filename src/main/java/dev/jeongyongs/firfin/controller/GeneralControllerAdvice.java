package dev.jeongyongs.firfin.controller;

import dev.jeongyongs.firfin.domain.ErrorCode;
import dev.jeongyongs.firfin.dto.ErrorResponse;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(TransactionTimedOutException.class)
    public ResponseEntity<ErrorResponse> handleTransactionTimedOutException() {
        ErrorResponse body = new ErrorResponse(ErrorCode.TIMEOUT, "요청 타임아웃", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getAllErrors()
                           .stream()
                           .map(DefaultMessageSourceResolvable::getDefaultMessage)
                           .collect(Collectors.joining(","));
        ErrorResponse body = new ErrorResponse(ErrorCode.INVALID_REQUEST, message, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException ex) {
        if (!"23505".equals(ex.getSQLState())) {
            throw ex;
        }
        ErrorResponse body = new ErrorResponse(ErrorCode.DUPLICATED, "중복 요청", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(body);
    }
}
