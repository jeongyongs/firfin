package dev.jeongyongs.firfin.dto;

import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PaymentExecuteRequest {

    @NotNull(message = "userId: null일 수 없습니다")
    @Min(value = 0L, message = "userId: 음수일 수 없습니다")
    private Long userId;

    @NotNull(message = "transactionId: null일 수 없습니다")
    private UUID transactionId;

    @NotNull(message = "merchantId: null일 수 없습니다")
    @Min(value = 0L, message = "merchantId: 음수일 수 없습니다")
    private Long merchantId;

    @NotNull(message = "amount: null일 수 없습니다")
    @Min(value = 0L, message = "amount: 음수일 수 없습니다")
    private Long amount;
}
