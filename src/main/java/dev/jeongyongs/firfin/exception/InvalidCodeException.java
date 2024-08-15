package dev.jeongyongs.firfin.exception;

public class InvalidCodeException extends RuntimeException {

    private static final String INVALID_CODE = "유효하지 않는 코드입니다: ";

    public InvalidCodeException(int code) {
        super(INVALID_CODE + code);
    }
}
