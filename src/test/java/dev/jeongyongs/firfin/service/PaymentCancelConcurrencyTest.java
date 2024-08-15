package dev.jeongyongs.firfin.service;

import static org.assertj.core.api.Assertions.assertThat;

import dev.jeongyongs.firfin.TestUtils;
import dev.jeongyongs.firfin.domain.User;
import dev.jeongyongs.firfin.dto.PaymentCancelRequest;
import dev.jeongyongs.firfin.repository.PaymentRepository;
import dev.jeongyongs.firfin.repository.UserRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentCancelConcurrencyTest {

    @Autowired
    PaymentCancelService paymentCancelService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @Test
    @DisplayName("결제 취소 동시 요청")
    void concurrency() throws Exception {
        // given
        int totalThread = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(totalThread);
        CountDownLatch countDownLatch = new CountDownLatch(totalThread);
        PaymentCancelRequest request = TestUtils.createDummyPaymentCancelRequest();
        AtomicInteger count = new AtomicInteger();
        long initMoney = userRepository.findById(1L)
                                       .orElseThrow()
                                       .getMoney();

        // when
        for (int i = 0; i < totalThread; i++) {
            executorService.submit(() -> {
                try {
                    paymentCancelService.cancel(request);
                } catch (Exception ignore) {
                    count.incrementAndGet();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        // then
        User user = userRepository.findById(1L)
                                  .orElseThrow();

        assertThat(count.get()).isEqualTo(totalThread - 1);
        assertThat(user.getMoney()).isEqualTo(initMoney + 10_000L);
    }
}
