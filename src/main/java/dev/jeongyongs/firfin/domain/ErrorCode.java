package dev.jeongyongs.firfin.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode implements Code {

    DUPLICATED(10),
    INSUFFICIENT_MONEY(20),
    EXCEED_TRANSACTION_LIMIT(21),
    EXCEED_DAILY_LIMIT(22),
    EXCEED_MONTHLY_LIMIT(23),
    TIMEOUT(30),
    INVALID_REQUEST(40),
    INVALID_USER(41),
    INVALID_PAYMENT(42),
    INVALID_PAYBACK(43),
    MISMATCH(50);

    @JsonValue
    private final int code;
}
