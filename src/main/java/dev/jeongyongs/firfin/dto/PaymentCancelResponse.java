package dev.jeongyongs.firfin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentCancelResponse {

    @JsonProperty("payment_id")
    private final long paymentId;

    private final LocalDateTime timestamp;
}
