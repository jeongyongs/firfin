package dev.jeongyongs.firfin.service.impl;

import dev.jeongyongs.firfin.domain.Payback;
import dev.jeongyongs.firfin.domain.Payment;
import dev.jeongyongs.firfin.domain.PaymentStatus;
import dev.jeongyongs.firfin.domain.User;
import dev.jeongyongs.firfin.dto.PaybackExecuteRequest;
import dev.jeongyongs.firfin.dto.PaybackExecuteResponse;
import dev.jeongyongs.firfin.exception.DuplicateRequestException;
import dev.jeongyongs.firfin.exception.PaymentNotFoundException;
import dev.jeongyongs.firfin.exception.UserIdMismatchException;
import dev.jeongyongs.firfin.exception.UserNotFoundException;
import dev.jeongyongs.firfin.repository.PaybackRepository;
import dev.jeongyongs.firfin.repository.PaymentRepository;
import dev.jeongyongs.firfin.repository.UserRepository;
import dev.jeongyongs.firfin.service.PaybackExecuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaybackExecuteServiceImpl implements PaybackExecuteService {

    private static final String DUPLICATED = "이미 페이백 지급된 건입니다";

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaybackRepository paybackRepository;

    @Transactional(timeout = 5)
    @Override
    public PaybackExecuteResponse execute(PaybackExecuteRequest request) {
        if (paybackRepository.existsByPaymentId(request.getPaymentId())) {
            throw new DuplicateRequestException(DUPLICATED);
        }
        User user = userRepository.findLockUserById(request.getUserId())
                                  .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        Payment payment = paymentRepository.findByIdAndPaymentStatus(request.getPaymentId(), PaymentStatus.APPROVE)
                                           .orElseThrow(() -> new PaymentNotFoundException(request.getPaymentId()));

        validateMatch(payment, user);

        user.setPoint(user.getPoint() + request.getAmount());
        Payback payback = new Payback(payment, request.getAmount());
        paybackRepository.save(payback);

        return new PaybackExecuteResponse(payback.getId(), payback.getAmount(), payback.getCreatedAt());
    }

    private void validateMatch(Payment payment, User user) {
        if (!payment.getUser().equals(user)) {
            throw new UserIdMismatchException();
        }
    }
}
