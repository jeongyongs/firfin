package dev.jeongyongs.firfin.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PaymentStatus {

    APPROVE(0),
    CANCELED(1);

    private final int value;
}
