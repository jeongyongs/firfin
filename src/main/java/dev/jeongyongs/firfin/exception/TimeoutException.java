package dev.jeongyongs.firfin.exception;

import dev.jeongyongs.firfin.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public class TimeoutException extends AbstractException {

    public TimeoutException(String message) {
        super(ErrorCode.TIMEOUT, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
