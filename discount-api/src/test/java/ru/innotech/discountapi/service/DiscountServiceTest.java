package ru.innotech.discountapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.innotech.discountapi.mapper.DiscountMapper;
import ru.innotech.discountapi.repository.DiscountRepository;
import ru.innotech.discountapi.utils.DiscountTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {
    @Mock
    private DiscountRepository discountRepository;
    @Mock
    private DiscountMapper discountMapper;
    @Mock
    private MetricsService metricsService;
    @InjectMocks
    private DiscountService discountService;

    private static final int MAX_FETCH_SIZE = 100_000;

    @BeforeEach
    void setUp() throws Exception {
        var configField = DiscountService.class.getDeclaredField("maxFetchSize");
        configField.setAccessible(true);
        configField.setInt(discountService, MAX_FETCH_SIZE);
    }

    @Test
    void getDiscounts() {
        //given
        var discountEntity1 = DiscountTestUtils.discount1Mock();
        var discountEntity2 = DiscountTestUtils.discount2Mock();
        var discountResponseDto1 = DiscountTestUtils.discountResponse1Mock();
        var discountResponseDto2 = DiscountTestUtils.discountResponse2Mock();
        when(discountRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(discountEntity1, discountEntity2)));
        when(discountMapper.toDto(discountEntity1)).thenReturn(discountResponseDto1);
        when(discountMapper.toDto(discountEntity2)).thenReturn(discountResponseDto2);

        //when
        var actualResult = discountService.getDiscounts();

        //then
        assertThat(actualResult).isEqualTo(List.of(discountResponseDto1, discountResponseDto2));
        verify(discountRepository).findAll(any(Pageable.class));
        verify(discountMapper).toDto(discountEntity1);
        verify(discountMapper).toDto(discountEntity2);
    }
}