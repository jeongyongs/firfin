package dev.jeongyongs.firfin.exception;

import dev.jeongyongs.firfin.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public class ExceedMonthlyLimitException extends AbstractException {

    private static final String EXCEED_MONTHLY_LIMIT = "월간 결제 금액이 초과되었습니다";

    public ExceedMonthlyLimitException() {
        super(ErrorCode.EXCEED_MONTHLY_LIMIT, HttpStatus.BAD_REQUEST, EXCEED_MONTHLY_LIMIT);
    }
}
