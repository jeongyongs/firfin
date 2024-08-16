package dev.jeongyongs.firfin.service;

import dev.jeongyongs.firfin.dto.PaybackExecuteRequest;
import dev.jeongyongs.firfin.dto.PaybackExecuteResponse;
import javax.validation.Valid;

public interface PaybackExecuteService {

    PaybackExecuteResponse execute(PaybackExecuteRequest request);
}
