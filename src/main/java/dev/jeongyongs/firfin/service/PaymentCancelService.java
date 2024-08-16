package dev.jeongyongs.firfin.service;

import dev.jeongyongs.firfin.dto.PaymentCancelRequest;
import dev.jeongyongs.firfin.dto.PaymentCancelResponse;

public interface PaymentCancelService {

    PaymentCancelResponse cancel(PaymentCancelRequest request);
}
