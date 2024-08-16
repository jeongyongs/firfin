package dev.jeongyongs.firfin.convertor;

import dev.jeongyongs.firfin.domain.PaybackStatus;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaybackStatusCodeConvertor extends AbstractCodeConvertor<PaybackStatus> {

    public PaybackStatusCodeConvertor() {
        super(PaybackStatus.class);
    }
}
