package dev.jeongyongs.firfin.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PaymentCancelRequest {

    @NotNull(message = "userId: null일 수 없습니다")
    @Min(value = 0L, message = "userId: 음수일 수 없습니다")
    private Long userId;

    @NotNull(message = "paymentId: null일 수 없습니다")
    @Min(value = 0L, message = "paymentId: 음수일 수 없습니다")
    private Long paymentId;
}
