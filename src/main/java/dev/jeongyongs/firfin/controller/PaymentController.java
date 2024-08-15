package dev.jeongyongs.firfin.controller;

import dev.jeongyongs.firfin.dto.PaymentExecuteRequest;
import dev.jeongyongs.firfin.dto.PaymentExecuteResponse;
import dev.jeongyongs.firfin.service.PaymentService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentExecuteResponse> doExecute(@Valid @RequestBody PaymentExecuteRequest request) {
        return ResponseEntity.ok(paymentService.execute(request));
    }
}
