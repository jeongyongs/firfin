package dev.jeongyongs.firfin.exception;

import dev.jeongyongs.firfin.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public class PaybackNotFoundException extends AbstractException {

    private static final String INVALID_PAYBACK = "유효하지 않는 페이백입니다: ";

    public PaybackNotFoundException(long paybackId) {
        super(ErrorCode.INVALID_PAYBACK, HttpStatus.NOT_FOUND, INVALID_PAYBACK + paybackId);
    }
}
