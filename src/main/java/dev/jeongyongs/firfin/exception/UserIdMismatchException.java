package dev.jeongyongs.firfin.exception;

import dev.jeongyongs.firfin.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserIdMismatchException extends AbstractException {

    private static final String MISMATCH = "결제 정보가 일치하지 않습니다";

    public UserIdMismatchException() {
        super(ErrorCode.MISMATCH, HttpStatus.BAD_REQUEST, MISMATCH);
    }
}
