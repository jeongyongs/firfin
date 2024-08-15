package dev.jeongyongs.firfin.controller;

import static dev.jeongyongs.firfin.TestUtils.createDummyPaymentExecuteRequest;
import static dev.jeongyongs.firfin.TestUtils.createDummyPaymentExecuteResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jeongyongs.firfin.domain.ErrorCode;
import dev.jeongyongs.firfin.dto.PaymentExecuteRequest;
import dev.jeongyongs.firfin.dto.PaymentExecuteResponse;
import dev.jeongyongs.firfin.exception.DuplicateRequestException;
import dev.jeongyongs.firfin.exception.ExceedDailyLimitException;
import dev.jeongyongs.firfin.exception.ExceedMonthlyLimitException;
import dev.jeongyongs.firfin.exception.ExceedTransactionLimitException;
import dev.jeongyongs.firfin.exception.InsufficientMoneyException;
import dev.jeongyongs.firfin.exception.TimeoutException;
import dev.jeongyongs.firfin.exception.UserNotFoundException;
import dev.jeongyongs.firfin.service.PaymentService;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    PaymentService paymentService;

    public static Stream<Arguments> errors() {
        return Stream.of(
            Arguments.of(
                new DuplicateRequestException("이미 결제된 건입니다"),
                "이미 결제된 건입니다",
                status().isConflict(),
                ErrorCode.DUPLICATED
            ),
            Arguments.of(
                new InsufficientMoneyException(),
                "잔액이 부족합니다",
                status().isBadRequest(),
                ErrorCode.INSUFFICIENT_MONEY
            ),
            Arguments.of(
                new ExceedTransactionLimitException(),
                "건당 결제 금액이 초과되었습니다",
                status().isBadRequest(),
                ErrorCode.EXCEED_TRANSACTION_LIMIT
            ),
            Arguments.of(
                new ExceedDailyLimitException(),
                "일일 결제 금액이 초과되었습니다",
                status().isBadRequest(),
                ErrorCode.EXCEED_DAILY_LIMIT
            ),
            Arguments.of(
                new ExceedMonthlyLimitException(),
                "월간 결제 금액이 초과되었습니다",
                status().isBadRequest(),
                ErrorCode.EXCEED_MONTHLY_LIMIT
            ),
            Arguments.of(
                new TimeoutException("결제 실행 시간이 초과되었습니다"),
                "결제 실행 시간이 초과되었습니다",
                status().isInternalServerError(),
                ErrorCode.TIMEOUT
            ),
            Arguments.of(
                new UserNotFoundException(1L),
                "유효하지 않는 유저입니다: 1",
                status().isNotFound(),
                ErrorCode.INVALID_USER
            )
        );
    }

    @Test
    @DisplayName("결제 성공 응답")
    void paymentExecuteSuccess() throws Exception {
        PaymentExecuteResponse response = createDummyPaymentExecuteResponse();
        given(paymentService.execute(any())).willReturn(response);

        mockMvc.perform(post("/api/payment")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(getRequestBody()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.paymentId").value(response.getPaymentId()))
               .andExpect(jsonPath("$.amount").value(response.getAmount()))
               .andExpect(jsonPath("$.createAt").exists());
    }

    @ParameterizedTest
    @MethodSource("errors")
    @DisplayName("결제 에러 응답")
    void paymentExecuteDuplicated(Throwable e, String message, ResultMatcher status, ErrorCode code) throws Exception {
        given(paymentService.execute(any())).willThrow(e);

        mockMvc.perform(post("/api/payment")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(getRequestBody()))
               .andExpect(status)
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.code").value(code.getCode()))
               .andExpect(jsonPath("$.message").value(message))
               .andExpect(jsonPath("$.timestamp").exists());
    }

    private String getRequestBody() throws Exception {
        PaymentExecuteRequest request = createDummyPaymentExecuteRequest();

        return objectMapper.writeValueAsString(request);
    }
}
