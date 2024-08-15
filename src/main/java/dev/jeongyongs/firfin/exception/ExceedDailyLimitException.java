package dev.jeongyongs.firfin.exception;

import dev.jeongyongs.firfin.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public class ExceedDailyLimitException extends AbstractException {

    private static final String EXCEED_DAILY_LIMIT = "일일 결제 금액이 초과되었습니다";

    public ExceedDailyLimitException() {
        super(ErrorCode.EXCEED_DAILY_LIMIT, HttpStatus.BAD_REQUEST, EXCEED_DAILY_LIMIT);
    }
}
