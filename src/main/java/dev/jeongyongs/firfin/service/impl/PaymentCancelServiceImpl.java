package dev.jeongyongs.firfin.service.impl;

import dev.jeongyongs.firfin.domain.Payment;
import dev.jeongyongs.firfin.domain.PaymentStatus;
import dev.jeongyongs.firfin.domain.User;
import dev.jeongyongs.firfin.dto.PaymentCancelRequest;
import dev.jeongyongs.firfin.dto.PaymentCancelResponse;
import dev.jeongyongs.firfin.exception.DuplicateRequestException;
import dev.jeongyongs.firfin.exception.PaymentNotFoundException;
import dev.jeongyongs.firfin.exception.UserIdMismatchException;
import dev.jeongyongs.firfin.exception.UserNotFoundException;
import dev.jeongyongs.firfin.repository.PaymentRepository;
import dev.jeongyongs.firfin.repository.UserRepository;
import dev.jeongyongs.firfin.service.PaymentCancelService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentCancelServiceImpl implements PaymentCancelService {

    private static final String DUPLICATED = "이미 결제 취소된 건입니다";

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Transactional(timeout = 5)
    @Override
    public PaymentCancelResponse cancel(PaymentCancelRequest request) {
        User user = userRepository.findLockUserById(request.getUserId())
                                  .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        Payment payment = paymentRepository.findById(request.getPaymentId())
                                           .orElseThrow(() -> new PaymentNotFoundException(request.getPaymentId()));

        validateMatch(payment, user);
        validateDuplicated(payment);

        user.setMoney(user.getMoney() + payment.getAmount());
        payment.setPaymentStatus(PaymentStatus.CANCELED);
        LocalDateTime updateAt = LocalDateTime.now();
        payment.setUpdateAt(updateAt);

        return new PaymentCancelResponse(payment.getId(), updateAt);
    }

    private void validateDuplicated(Payment payment) {
        if (payment.getPaymentStatus() == PaymentStatus.CANCELED) {
            throw new DuplicateRequestException(DUPLICATED);
        }
    }

    private void validateMatch(Payment payment, User user) {
        if (!payment.getUser().equals(user)) {
            throw new UserIdMismatchException();
        }
    }
}

