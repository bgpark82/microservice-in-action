package com.bgpark.order.domain.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemClient itemClient;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    void create() throws Exception {
        given(itemClient.order(any(), any())).willReturn(orderItemDto());

        MockHttpServletResponse response = mvc.perform(
                    post("/order-service/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(orderCreateDto())))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    private OrderItemDto orderItemDto() {
        OrderItemDto.OrderOptionDto option = OrderItemDto.OrderOptionDto.builder()
                .optionPrice(16_900)
                .optionName("d-day calendar")
                .optionAmount(1)
                .build();

        return OrderItemDto.builder()
                .itemId(1L)
                .itemName("babyface")
                .itemPrice(36_000)
                .itemAmount(1)
                .options(Arrays.asList(option))
                .build();
    }

    private OrderCreateDto orderCreateDto() {
        return OrderCreateDto.builder()
                .itemId(1L)
                .itemAmount(1)
                .options(Arrays.asList(OrderCreateDto.OrderCreateOptionDto.builder()
                        .optionId(1L)
                        .OptionAmount(2)
                        .build()))
                .build();
    }
}
