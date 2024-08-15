package dev.jeongyongs.firfin.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum UserStatus implements Code {

    ACTIVE(0),
    INACTIVE(1);

    private final int code;
}
