package dev.jeongyongs.firfin.service.impl;

import dev.jeongyongs.firfin.domain.Payment;
import dev.jeongyongs.firfin.domain.PaymentStatus;
import dev.jeongyongs.firfin.domain.User;
import dev.jeongyongs.firfin.dto.PaymentExecuteRequest;
import dev.jeongyongs.firfin.dto.PaymentExecuteResponse;
import dev.jeongyongs.firfin.exception.DuplicateRequestException;
import dev.jeongyongs.firfin.exception.ExceedDailyLimitException;
import dev.jeongyongs.firfin.exception.ExceedMonthlyLimitException;
import dev.jeongyongs.firfin.exception.ExceedTransactionLimitException;
import dev.jeongyongs.firfin.exception.InsufficientMoneyException;
import dev.jeongyongs.firfin.exception.UserNotFoundException;
import dev.jeongyongs.firfin.repository.PaymentRepository;
import dev.jeongyongs.firfin.repository.UserRepository;
import dev.jeongyongs.firfin.service.PaymentExecuteService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentExecuteServiceImpl implements PaymentExecuteService {

    private static final String DUPLICATED = "이미 결제된 건입니다";

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Transactional(timeout = 5)
    @Override
    public PaymentExecuteResponse execute(PaymentExecuteRequest request) {
        if (paymentRepository.existsByTransactionId(request.getTransactionId())) {
            throw new DuplicateRequestException(DUPLICATED);
        }
        User user = userRepository.findLockUserById(request.getUserId())
                                  .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        Payment payment = Payment.builder()
                                 .transactionId(request.getTransactionId())
                                 .user(user)
                                 .amount(request.getAmount())
                                 .merchantId(request.getMerchantId())
                                 .build();

        validateMoney(user, payment);
        validateTransactionLimit(user, payment);
        validateDailyLimit(user, payment);
        validateMonthlyLimit(user, payment);

        user.setMoney(calculateMoney(user, payment));
        paymentRepository.save(payment);

        return new PaymentExecuteResponse(payment.getId(), payment.getAmount(), payment.getCreateAt());
    }

    private void validateMoney(User user, Payment payment) {
        if (calculateMoney(user, payment) < 0) {
            throw new InsufficientMoneyException();
        }
    }

    private void validateDailyLimit(User user, Payment payment) {
        long dailyUsage = paymentRepository.getUsage(user, LocalDate.now().atStartOfDay())
                                           .orElse(0L);
        if (dailyUsage + payment.getAmount() > user.getDailyPaymentLimit()) {
            throw new ExceedDailyLimitException();
        }
    }

    private void validateMonthlyLimit(User user, Payment payment) {
        long monthlyUsage = paymentRepository.getUsage(user, LocalDate.now().withDayOfMonth(1).atStartOfDay())
                                             .orElse(0L);
        if (monthlyUsage + payment.getAmount() > user.getMonthlyPaymentLimit()) {
            throw new ExceedMonthlyLimitException();
        }
    }

    private void validateTransactionLimit(User user, Payment payment) {
        if (payment.getAmount() > user.getPerPaymentLimit()) {
            throw new ExceedTransactionLimitException();
        }
    }

    private long calculateMoney(User user, Payment payment) {
        return user.getMoney() - payment.getAmount();
    }
}
