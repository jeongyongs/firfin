package dev.jeongyongs.firfin.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentExecuteResponse {

    private final long paymentId;

    private final long amount;

    private final LocalDateTime createAt;
}
