package dev.jeongyongs.firfin.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentCancelResponse {

    private final long paymentId;

    private final LocalDateTime timestamp;
}
