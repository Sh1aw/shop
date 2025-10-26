package ru.innotech.discountapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import ru.innotech.discountapi.AbstractIntegrationTest;
import ru.innotech.discountapi.dto.DiscountResponse;
import ru.innotech.discountapi.utils.DiscountTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

public class DiscountServiceIntegrationTest extends AbstractIntegrationTest {

    @MockitoSpyBean
    private DiscountService discountService;

    @Test
    void whenGetDiscountsThenSuccess() {
        //given
        var discountResponse1 = DiscountTestUtils.discountResponse1Mock();
        var discountResponse2 = DiscountTestUtils.discountResponse2Mock();
        var discount1 = DiscountTestUtils.discount1Mock();
        var discount2 = DiscountTestUtils.discount2Mock();
        var expected = List.of(discountResponse1, discountResponse2);
        discountRepository.saveAll(List.of(discount1, discount2));

        //when
        var actualResult = discountService.getDiscounts();

        //when
        assertEquals(expected, actualResult);
    }

    @Test
    @SuppressWarnings({"unchecked"})
    void whenGetDiscountsThenSaveListToCache() {
        //given
        var discountResponse1 = DiscountTestUtils.discountResponse1Mock();
        var discountResponse2 = DiscountTestUtils.discountResponse2Mock();
        var discount1 = DiscountTestUtils.discount1Mock();
        var discount2 = DiscountTestUtils.discount2Mock();
        var expected = List.of(discountResponse1, discountResponse2);
        discountRepository.saveAll(List.of(discount1, discount2));

        //when
        var result = discountService.getDiscounts();

        //then
        var cacheValues = (List<DiscountResponse>) cacheManager.getCache("discountsListCache")
                .get("discountsList")
                .get();
        assertEquals(2, result.size());
        assertEquals(expected, cacheValues);
    }

    @Test
    void whenRepeatedGetDiscountsThenReturnValueFromCache() {
        //given
        var discount1 = DiscountTestUtils.discount1Mock();
        var discount2 = DiscountTestUtils.discount2Mock();
        discountRepository.saveAll(List.of(discount1, discount2));

        //when
        discountService.getDiscounts();
        discountService.getDiscounts();

        //then
        verify(discountRepository, Mockito.times(1)).findAll(any(Pageable.class));
    }
}
