package dev.jeongyongs.firfin.convertor;

import dev.jeongyongs.firfin.domain.Payback;
import dev.jeongyongs.firfin.domain.PaybackStatus;
import dev.jeongyongs.firfin.domain.PaymentStatus;
import dev.jeongyongs.firfin.domain.UserStatus;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaybackStatusCodeConvertor extends AbstractCodeConvertor<PaybackStatus> {

    public PaybackStatusCodeConvertor() {
        super(PaybackStatus.class);
    }
}
