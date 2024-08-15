package dev.jeongyongs.firfin.exception;

import dev.jeongyongs.firfin.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public class PaymentNotFoundException extends AbstractException {

    private static final String INVALID_PAYMENT = "유효하지 않는 결제입니다: ";

    public PaymentNotFoundException(long paymentId) {
        super(ErrorCode.INVALID_PAYMENT, HttpStatus.NOT_FOUND, INVALID_PAYMENT + paymentId);
    }
}
