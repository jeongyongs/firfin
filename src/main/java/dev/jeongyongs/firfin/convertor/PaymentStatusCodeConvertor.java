package dev.jeongyongs.firfin.convertor;

import dev.jeongyongs.firfin.domain.PaymentStatus;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentStatusCodeConvertor extends AbstractCodeConvertor<PaymentStatus> {

    public PaymentStatusCodeConvertor() {
        super(PaymentStatus.class);
    }
}
