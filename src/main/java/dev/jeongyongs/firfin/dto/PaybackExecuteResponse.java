package dev.jeongyongs.firfin.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaybackExecuteResponse {

    private final long paybackId;

    private final long amount;

    private final LocalDateTime createAt;
}
