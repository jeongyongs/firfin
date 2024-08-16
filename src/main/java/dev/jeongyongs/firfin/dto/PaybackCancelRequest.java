package dev.jeongyongs.firfin.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PaybackCancelRequest {

    @NotNull(message = "userId: null일 수 없습니다")
    @Min(value = 0L, message = "userId: 음수일 수 없습니다")
    private Long userId;

    @NotNull(message = "paybackId: null일 수 없습니다")
    @Min(value = 0L, message = "paybackId: 음수일 수 없습니다")
    private Long paybackId;
}
