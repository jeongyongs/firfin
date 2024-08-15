package dev.jeongyongs.firfin.exception;

import dev.jeongyongs.firfin.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public class DuplicateRequestException extends AbstractException {

    public DuplicateRequestException(String message) {
        super(ErrorCode.DUPLICATED, HttpStatus.CONFLICT, message);
    }
}
