package dev.jeongyongs.firfin.service;

import dev.jeongyongs.firfin.dto.PaybackExecuteRequest;
import dev.jeongyongs.firfin.dto.PaybackExecuteResponse;

public interface PaybackExecuteService {

    PaybackExecuteResponse execute(PaybackExecuteRequest request);
}
