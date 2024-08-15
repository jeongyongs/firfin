package dev.jeongyongs.firfin.service;

import static org.assertj.core.api.Assertions.assertThat;

import dev.jeongyongs.firfin.TestUtils;
import dev.jeongyongs.firfin.domain.User;
import dev.jeongyongs.firfin.dto.PaymentExecuteRequest;
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
class PaymentConcurrencyTest {

    @Autowired
    PaymentService paymentService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("동시 요청")
    void concurrency() throws Exception {
        // given
        int totalThread = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(totalThread);
        CountDownLatch countDownLatch = new CountDownLatch(totalThread);
        PaymentExecuteRequest request = TestUtils.createDummyPaymentExecuteRequest();
        AtomicInteger count = new AtomicInteger();

        // when
        for (int i = 0; i < totalThread; i++) {
            executorService.submit(() -> {
                try {
                    paymentService.execute(request);
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
        assertThat(user.getMoney()).isEqualTo(9_000L);
    }
}
