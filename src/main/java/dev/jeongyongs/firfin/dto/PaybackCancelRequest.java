package dev.jeongyongs.firfin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PaybackCancelRequest {

    @NotNull(message = "user_id: null일 수 없습니다")
    @Min(value = 0L, message = "user_id: 음수일 수 없습니다")
    @JsonProperty("user_id")
    private Long userId;

    @NotNull(message = "payback_id: null일 수 없습니다")
    @Min(value = 0L, message = "payback_id: 음수일 수 없습니다")
    @JsonProperty("payback_id")
    private Long paybackId;
}
