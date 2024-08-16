package dev.jeongyongs.firfin.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaybackCancelResponse {

    private final long paybackId;

    private final LocalDateTime timestamp;
}
