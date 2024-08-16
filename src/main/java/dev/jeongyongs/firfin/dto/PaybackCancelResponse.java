package dev.jeongyongs.firfin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaybackCancelResponse {

    @JsonProperty("payback_id")
    private final long paybackId;

    private final LocalDateTime timestamp;
}
