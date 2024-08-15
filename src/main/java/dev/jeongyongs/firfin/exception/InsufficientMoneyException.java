package dev.jeongyongs.firfin.exception;

import dev.jeongyongs.firfin.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public class InsufficientMoneyException extends AbstractException {

    private static final String INSUFFICIENT_MONEY = "잔액이 부족합니다";

    public InsufficientMoneyException() {
        super(ErrorCode.INSUFFICIENT_MONEY, HttpStatus.BAD_REQUEST, INSUFFICIENT_MONEY);
    }
}
