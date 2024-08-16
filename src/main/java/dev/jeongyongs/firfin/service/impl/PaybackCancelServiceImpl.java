package dev.jeongyongs.firfin.service.impl;

import dev.jeongyongs.firfin.domain.Payback;
import dev.jeongyongs.firfin.domain.PaybackStatus;
import dev.jeongyongs.firfin.domain.User;
import dev.jeongyongs.firfin.dto.PaybackCancelRequest;
import dev.jeongyongs.firfin.dto.PaybackCancelResponse;
import dev.jeongyongs.firfin.exception.DuplicateRequestException;
import dev.jeongyongs.firfin.exception.PaybackNotFoundException;
import dev.jeongyongs.firfin.exception.UserIdMismatchException;
import dev.jeongyongs.firfin.exception.UserNotFoundException;
import dev.jeongyongs.firfin.repository.PaybackRepository;
import dev.jeongyongs.firfin.repository.UserRepository;
import dev.jeongyongs.firfin.service.PaybackCancelService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaybackCancelServiceImpl implements PaybackCancelService {

    private static final String DUPLICATED = "이미 페이백 취소된 건입니다";

    private final UserRepository userRepository;
    private final PaybackRepository paybackRepository;

    @Transactional(timeout = 5)
    @Override
    public PaybackCancelResponse cancel(PaybackCancelRequest request) {
        User user = userRepository.findLockUserById(request.getUserId())
                                  .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        Payback payback = paybackRepository.findById(request.getPaybackId())
                                           .orElseThrow(() -> new PaybackNotFoundException(request.getPaybackId()));

        validateMatch(payback, user);
        validateDuplicated(payback);

        long newPoint = user.getPoint() - payback.getAmount();
        user.setPoint(newPoint < 0 ? 0 : newPoint);
        payback.setPaybackStatus(PaybackStatus.CANCELED);
        LocalDateTime updateAt = LocalDateTime.now();
        payback.setUpdateAt(updateAt);

        return new PaybackCancelResponse(payback.getId(), updateAt);
    }

    private void validateDuplicated(Payback payback) {
        if (payback.getPaybackStatus() == PaybackStatus.CANCELED) {
            throw new DuplicateRequestException(DUPLICATED);
        }
    }

    private void validateMatch(Payback payback, User user) {
        if (!payback.getPayment().getUser().equals(user)) {
            throw new UserIdMismatchException();
        }
    }
}
