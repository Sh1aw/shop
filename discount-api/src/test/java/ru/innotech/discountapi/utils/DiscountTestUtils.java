package ru.innotech.discountapi.utils;

import lombok.experimental.UtilityClass;
import ru.innotech.discountapi.dto.DiscountResponse;
import ru.innotech.discountapi.entity.Discount;

import java.math.BigDecimal;

@UtilityClass
public class DiscountTestUtils {

    public static Discount discount1Mock() {
        return Discount.builder()
                .productId(1L)
                .discount(new BigDecimal("10.00"))
                .build();
    }

    public static Discount discount2Mock() {
        return Discount.builder()
                .productId(2L)
                .discount(new BigDecimal("20.00"))
                .build();
    }

    public static DiscountResponse discountResponse1Mock() {
        return new DiscountResponse(1L, new BigDecimal("10.00"));
    }

    public static DiscountResponse discountResponse2Mock() {
        return new DiscountResponse(2L, new BigDecimal("20.00"));
    }
}
