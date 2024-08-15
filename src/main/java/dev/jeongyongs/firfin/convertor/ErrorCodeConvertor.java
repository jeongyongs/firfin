package dev.jeongyongs.firfin.convertor;

import dev.jeongyongs.firfin.domain.ErrorCode;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ErrorCodeConvertor extends AbstractCodeConvertor<ErrorCode> {

    public ErrorCodeConvertor() {
        super(ErrorCode.class);
    }
}
