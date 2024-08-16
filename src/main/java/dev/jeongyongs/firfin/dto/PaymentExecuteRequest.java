package dev.jeongyongs.firfin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PaymentExecuteRequest {

    @NotNull(message = "user_id: null일 수 없습니다")
    @Min(value = 0L, message = "user_id: 음수일 수 없습니다")
    @JsonProperty("user_id")
    private Long userId;

    @NotNull(message = "transaction_id: null일 수 없습니다")
    @JsonProperty("transaction_id")
    private UUID transactionId;

    @NotNull(message = "merchant_id: null일 수 없습니다")
    @Min(value = 0L, message = "merchant_id: 음수일 수 없습니다")
    @JsonProperty("merchant_id")
    private Long merchantId;

    @NotNull(message = "amount: null일 수 없습니다")
    @Min(value = 0L, message = "amount: 음수일 수 없습니다")
    private Long amount;
}
