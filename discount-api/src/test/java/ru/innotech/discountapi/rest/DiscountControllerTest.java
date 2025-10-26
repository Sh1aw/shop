package ru.innotech.discountapi.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.innotech.discountapi.AbstractIntegrationTest;
import ru.innotech.discountapi.utils.DiscountTestUtils;

import static org.hamcrest.Matchers.hasSize;

class DiscountControllerTest extends AbstractIntegrationTest {

    @Test
    void whenGetAllDiscountsThenSuccess() throws Exception {
        //given
        var discount1 = DiscountTestUtils.discount1Mock();
        var discount2 = DiscountTestUtils.discount2Mock();
        discountRepository.save(discount1);
        discountRepository.save(discount2);

        //when
        //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/discounts")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$", hasSize(2))
        );
    }
}