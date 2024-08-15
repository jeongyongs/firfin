package dev.jeongyongs.firfin.exception;

import dev.jeongyongs.firfin.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AbstractException {

    private static final String INVALID_USER = "유효하지 않는 유저입니다: ";

    public UserNotFoundException(long userId) {
        super(ErrorCode.INVALID_USER, HttpStatus.NOT_FOUND, INVALID_USER + userId);
    }
}
