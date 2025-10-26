package ru.innotech.discountapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innotech.discountapi.dto.DiscountResponse;
import ru.innotech.discountapi.entity.Discount;
import ru.innotech.discountapi.mapper.DiscountMapper;
import ru.innotech.discountapi.repository.DiscountRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.innotech.discountapi.config.RedisCacheConfig.DISCOUNTS_CACHE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final MetricsService metricsService;
    @Value("${app.discount.max-fetch-size:1000}")
    private int maxFetchSize;

    @Transactional(readOnly = true)
    @Cacheable(value = DISCOUNTS_CACHE_NAME, key = "'discountsList'")
    public List<DiscountResponse> getDiscounts() {
        try (var op = MDC.putCloseable("op", "getDiscounts")) {
            log.debug("Get discounts: fetching all");
            var foundDiscounts = discountRepository.findAll(Pageable.ofSize(maxFetchSize));
            if (foundDiscounts.getTotalElements() > maxFetchSize + 1) {
                log.warn("Total Number of entities exceeds configured max-fetch-size: {}", maxFetchSize);
            }
            try (var size = MDC.putCloseable("batchSize", String.valueOf(foundDiscounts.getSize()))) {
                log.info("Get products: returned {}", foundDiscounts.getSize());
            }

            return foundDiscounts
                    .stream()
                    .map(discountMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            metricsService.incrementErrorCounter("getDiscounts");
            log.error("Get discounts: failed - {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    @CacheEvict(value = DISCOUNTS_CACHE_NAME, allEntries = true)
    public Long createDiscount(Discount discount) {
        try (var op = MDC.putCloseable("op", "createDiscount")) {
            return discountRepository.save(discount).getId();
        } catch (Exception e) {
            metricsService.incrementErrorCounter("createDiscount");
            log.error("Create discount : failed - {}", e.getMessage());
            throw e;
        }
    }
}
