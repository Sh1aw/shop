package ru.innotech.discountapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record DiscountResponse(
        Long productId,
        BigDecimal discount
) implements Serializable {
}
