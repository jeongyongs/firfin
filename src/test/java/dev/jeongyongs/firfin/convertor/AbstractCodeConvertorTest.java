package dev.jeongyongs.firfin.convertor;

import static org.assertj.core.api.Assertions.assertThat;

import dev.jeongyongs.firfin.domain.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AbstractCodeConvertorTest {

    @RequiredArgsConstructor
    @Getter
    enum TestEnum implements Code {
        A(1),
        B(2);

        private final int code;
    }

    static class TestConvertor extends AbstractCodeConvertor<TestEnum> {

        public TestConvertor() {
            super(TestEnum.class);
        }
    }

    AbstractCodeConvertor<TestEnum> convertor = new TestConvertor();

    @Test
    @DisplayName("값으로 변환")
    void convertToDatabaseColumn() {
        for (TestEnum testEnum : TestEnum.values()) {
            Integer code = convertor.convertToDatabaseColumn(testEnum);

            assertThat(code).isEqualTo(testEnum.getCode());
        }
    }

    @Test
    @DisplayName("객체로 변환")
    void convertToEntityAttribute() {
        for (TestEnum testEnum : TestEnum.values()) {
            TestEnum object = convertor.convertToEntityAttribute(testEnum.getCode());

            assertThat(object).isEqualTo(testEnum);
        }
    }
}
