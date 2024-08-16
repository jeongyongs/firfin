package dev.jeongyongs.firfin.service;

import dev.jeongyongs.firfin.dto.PaybackCancelRequest;
import dev.jeongyongs.firfin.dto.PaybackCancelResponse;

public interface PaybackCancelService {

    PaybackCancelResponse cancel(PaybackCancelRequest request);
}
