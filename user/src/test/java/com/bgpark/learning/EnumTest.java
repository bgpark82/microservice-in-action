package com.bgpark.learning;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumTest {

    @Test
    void 코드에_따라_서로_다른_계산하기_기존방식() {
        LegacyCalculator calculator = new LegacyCalculator();
        String code = "CALC_B";
        int originValue = 1000;

        assertThat(calculator.calculate(code, originValue)).isEqualTo(10_000L);
    }

    @Test
    void 코드에_따라_서로_다른_계산하기_새로운방식() {
        CalculatorType code = CalculatorType.CALC_B;
        int originValue = 1000;

        assertThat(code.calculate(originValue)).isEqualTo(10_000L);
    }

    class LegacyCalculator {

        // code에 따라 지정된 메소드만 계산하길 원함
        // String을 인자로 받고 Long 타입을 리턴하면 모두 사용가능한 메소드
        // 똑같은 기능을 하는 메소드 중복 생성 -> 여러개가 생성되면 뭘 써야되는지 모름
        public long calculate(String code, long originValue) {
            if ("CALC_A".equals(code)) {
                return originValue;
            } else if ("CALC_B".equals(code)) {
                return originValue * 10;
            } else if ("CALC_C".equals(code)) {
                return originValue * 3;
            } else {
                return 0;
            }
        }
    }

    enum CalculatorType {
        CALC_A(value -> value),
        CALC_B(value -> value * 10),
        CALC_C(value -> value * 3),
        CALC_ETC(value -> 0L);

        private Function<Long, Long> expression;

        CalculatorType(Function<Long, Long> expression) {
            this.expression = expression;
        }

        public long calculate(long value) {
            return expression.apply(value);
        }
    }
}


