package ru.innotech.discountapi.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.innotech.discountapi.dto.DiscountResponse;
import ru.innotech.discountapi.service.DiscountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @GetMapping
    public ResponseEntity<List<DiscountResponse>> getDiscounts() {
        return ResponseEntity.ok(discountService.getDiscounts());
    }
}
