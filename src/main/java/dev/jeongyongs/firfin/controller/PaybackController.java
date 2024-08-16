package dev.jeongyongs.firfin.controller;

import dev.jeongyongs.firfin.dto.PaybackExecuteRequest;
import dev.jeongyongs.firfin.dto.PaybackExecuteResponse;
import dev.jeongyongs.firfin.service.PaybackExecuteService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payback")
@RequiredArgsConstructor
public class PaybackController {

    private final PaybackExecuteService paybackExecuteService;

    @PostMapping
    public ResponseEntity<PaybackExecuteResponse> doExecute(@Valid @RequestBody PaybackExecuteRequest request) {
        return ResponseEntity.ok(paybackExecuteService.execute(request));
    }
}
