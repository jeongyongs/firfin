package dev.jeongyongs.firfin;

import static org.springframework.test.util.ReflectionTestUtils.setField;

import dev.jeongyongs.firfin.domain.Payment;
import dev.jeongyongs.firfin.domain.User;
import dev.jeongyongs.firfin.dto.PaymentExecuteRequest;
import dev.jeongyongs.firfin.dto.PaymentExecuteResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 더미 객체 생성 등 테스트를 위한 메서드를 제공하는 유틸 클래스입니다.
 */
public class TestUtils {

    public static PaymentExecuteRequest createDummyPaymentExecuteRequest(long amount) throws Exception {
        PaymentExecuteRequest request = newInstance(PaymentExecuteRequest.class);

        setField(request, "userId", 1L);
        setField(request, "transactionId", UUID.randomUUID());
        setField(request, "merchantId", 2L);
        setField(request, "amount", amount);

        return request;
    }

    public static PaymentExecuteRequest createDummyPaymentExecuteRequest() throws Exception {
        return createDummyPaymentExecuteRequest(1_000L);
    }

    public static PaymentExecuteResponse createDummyPaymentExecuteResponse() {
        return new PaymentExecuteResponse(1L, 2_000L, LocalDateTime.now());
    }

    private static <T> T newInstance(Class<T> clazz) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);

        return constructor.newInstance();
    }

    public static Payment createDummyPayment(User user, PaymentExecuteRequest request) throws Exception {
        Payment payment = Payment.builder()
                                 .amount(request.getAmount())
                                 .merchantId(request.getMerchantId())
                                 .transactionId(request.getTransactionId())
                                 .user(user)
                                 .build();
        Field field = payment.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(payment, 1L);

        return payment;
    }

    public static User createDummyUser() throws Exception {
        User user = User.builder()
                        .moneyLimit(1_000_000L)
                        .money(10_000L)
                        .point(0L)
                        .perPaymentLimit(20_000L)
                        .dailyPaymentLimit(50_000L)
                        .monthlyPaymentLimit(100_000L)
                        .build();
        Field field = user.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(user, 1L);

        return user;
    }
}
