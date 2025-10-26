package ru.innotech.discountapi.mapper;

import org.junit.jupiter.api.Test;
import ru.innotech.discountapi.dto.DiscountResponse;
import ru.innotech.discountapi.entity.Discount;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountMapperTest {
    private final DiscountMapper discountMapper = new DiscountMapperImpl();

    @Test
    void toDto() {
        //given
        var discountEntity = Discount.builder()
                .id(1L)
                .productId(2L)
                .discount(BigDecimal.TEN)
                .build();
        var expected = new DiscountResponse(2L, BigDecimal.TEN);

        //when
        var actualResult = discountMapper.toDto(discountEntity);

        //then
        assertThat(actualResult).isEqualTo(expected);
    }
}