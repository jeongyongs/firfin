package dev.jeongyongs.firfin.exception;

import dev.jeongyongs.firfin.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public class ExceedTransactionLimitException extends AbstractException {

    private static final String EXCEED_TRANSACTION_LIMIT = "건당 결제 금액이 초과되었습니다";

    public ExceedTransactionLimitException() {
        super(ErrorCode.EXCEED_TRANSACTION_LIMIT, HttpStatus.BAD_REQUEST, EXCEED_TRANSACTION_LIMIT);
    }
}
