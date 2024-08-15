package dev.jeongyongs.firfin.service;

import dev.jeongyongs.firfin.dto.PaymentExecuteResponse;
import dev.jeongyongs.firfin.dto.PaymentExecuteRequest;

public interface PaymentService {

    PaymentExecuteResponse execute(PaymentExecuteRequest request);
}
