package ru.innotech.discountapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.innotech.discountapi.dto.DiscountResponse;
import ru.innotech.discountapi.entity.Discount;

import java.math.BigDecimal;

@Mapper(componentModel = "spring",
        imports = {BigDecimal.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiscountMapper {
    DiscountResponse toDto(Discount discount);
}
