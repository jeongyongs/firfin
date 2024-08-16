package dev.jeongyongs.firfin.service;

import dev.jeongyongs.firfin.dto.PaymentExecuteRequest;
import dev.jeongyongs.firfin.dto.PaymentExecuteResponse;

public interface PaymentExecuteService {

    PaymentExecuteResponse execute(PaymentExecuteRequest request);
}
