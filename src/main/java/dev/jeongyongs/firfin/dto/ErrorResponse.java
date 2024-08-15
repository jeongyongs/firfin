package dev.jeongyongs.firfin.dto;

import dev.jeongyongs.firfin.domain.ErrorCode;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private final ErrorCode code;

    private final String message;

    private final LocalDateTime timestamp;
}
