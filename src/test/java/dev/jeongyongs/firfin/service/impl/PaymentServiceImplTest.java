package dev.jeongyongs.firfin.service.impl;

import static dev.jeongyongs.firfin.TestUtils.createDummyPayment;
import static dev.jeongyongs.firfin.TestUtils.createDummyPaymentExecuteRequest;
import static dev.jeongyongs.firfin.TestUtils.createDummyUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import dev.jeongyongs.firfin.domain.Payment;
import dev.jeongyongs.firfin.domain.User;
import dev.jeongyongs.firfin.dto.PaymentExecuteRequest;
import dev.jeongyongs.firfin.dto.PaymentExecuteResponse;
import dev.jeongyongs.firfin.exception.ExceedDailyLimitException;
import dev.jeongyongs.firfin.exception.ExceedMonthlyLimitException;
import dev.jeongyongs.firfin.exception.ExceedTransactionLimitException;
import dev.jeongyongs.firfin.exception.InsufficientMoneyException;
import dev.jeongyongs.firfin.exception.UserNotFoundException;
import dev.jeongyongs.firfin.repository.PaymentRepository;
import dev.jeongyongs.firfin.repository.UserRepository;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.stream.Stream;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl service;
    @Mock
    UserRepository userRepository;
    @Mock
    PaymentRepository paymentRepository;

    public static Stream<Arguments> exceed() {
        return Stream.of(
            Arguments.of("perPaymentLimit", 1_000L, ExceedTransactionLimitException.class),
            Arguments.of("dailyPaymentLimit", 1_000L, ExceedDailyLimitException.class),
            Arguments.of("monthlyPaymentLimit", 1_000L, ExceedMonthlyLimitException.class)
        );
    }

    @Test
    @DisplayName("결제 성공")
    void paymentExecuteSuccess() throws Exception {
        // given
        PaymentExecuteRequest request = createDummyPaymentExecuteRequest(1_000L);
        User user = createDummyUser();
        long initMoney = user.getMoney();
        Payment payment = createDummyPayment(user, request);

        given(paymentRepository.existsByTransactionId(any())).willReturn(false);
        given(userRepository.findLockUserById(any())).willReturn(Optional.of(user));
        given(paymentRepository.save(any())).willAnswer(setPaymentId());

        // when
        PaymentExecuteResponse response = service.execute(request);

        // then
        verify(paymentRepository).existsByTransactionId(any());
        verify(userRepository).findLockUserById(any());
        verify(paymentRepository).save(any());

        assertThat(response.getPaymentId()).isEqualTo(payment.getId());
        assertThat(response.getAmount()).isEqualTo(payment.getAmount());
        assertThat(response.getCreateAt()).isNotNull();
        assertThat(user.getMoney()).isEqualTo(initMoney - payment.getAmount());
    }

    private Answer<Object> setPaymentId() {
        return invocation -> {
            Payment payment = invocation.getArgument(0, Payment.class);
            Field field = payment.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(payment, 1L);

            return payment;
        };
    }

    @Test
    @DisplayName("잔액 부족")
    void insufficientMoney() throws Exception {
        // given
        given(paymentRepository.existsByTransactionId(any())).willReturn(false);
        given(userRepository.findLockUserById(any())).willReturn(Optional.of(createDummyUser()));

        // when
        ThrowingCallable when = () -> service.execute(createDummyPaymentExecuteRequest(10_001L));

        // then
        assertThatThrownBy(when).isInstanceOf(InsufficientMoneyException.class);
    }

    @ParameterizedTest
    @MethodSource("exceed")
    @DisplayName("한도 초과")
    void exceedLimit(String field, long limit, Class<Exception> clazz) throws Exception {
        // given
        User user = createDummyUser();
        setField(user, field, limit);
        given(paymentRepository.existsByTransactionId(any())).willReturn(false);
        given(userRepository.findLockUserById(any())).willReturn(Optional.of(user));

        // when
        ThrowingCallable when = () -> service.execute(createDummyPaymentExecuteRequest(10_000L));

        // then
        assertThatThrownBy(when).isInstanceOf(clazz);
    }

    @Test
    @DisplayName("유효하지 않는 유저")
    void userNotFound() {
        // given
        given(userRepository.findLockUserById(any())).willReturn(Optional.empty());

        // when
        ThrowingCallable when = () -> service.execute(createDummyPaymentExecuteRequest(10_000L));

        // then
        assertThatThrownBy(when).isInstanceOf(UserNotFoundException.class);
    }
}
