package com.readingisgood.Service;

import com.google.gson.Gson;
import com.readingisgood.Service.Controller.OrderController;
import com.readingisgood.Service.Data.DTO.BookOrder;
import com.readingisgood.Service.Model.Request.OrderBookRequest;
import com.readingisgood.Service.Model.Request.PersistCustomerRequest;
import com.readingisgood.Service.Model.Response.Customer.PersistCustomerResponse;
import com.readingisgood.Service.Model.Response.Order.OrderBookResponse;
import com.readingisgood.Service.Service.Interface.IOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private OrderController controller;

    @MockBean
    private IOrderService service;

    private MockMvc mockMvc;

    @Autowired
    protected Gson gson;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    /**
     * Create Order
     */
    @Test
    public void createOrderService(){

        OrderBookResponse response = OrderBookResponse.builder().build();
        when(service.persist(any(OrderBookRequest.class))).thenReturn(response);

    }
    @Test
    public void createOrderController() throws Exception {

        OrderBookRequest request = new OrderBookRequest();
        request.setCustomerId("111111111111111111111111");
        List<BookOrder> listOrder = new ArrayList<>(1);
        BookOrder bookOrder = BookOrder.builder().bookId("111").count(3).build();
        listOrder.add(bookOrder);
        request.setOrders(listOrder);


        this.mockMvc.perform(post("/api/orders/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isCreated());
    }
    @Test
    public void createOrderControllerInvalidParameter() throws Exception {

        OrderBookRequest request = new OrderBookRequest();
        request.setCustomerId("111111111111111");
        List<BookOrder> listOrder = new ArrayList<>(1);
        BookOrder bookOrder = BookOrder.builder().bookId("111").count(3).build();
        listOrder.add(bookOrder);
        request.setOrders(listOrder);


        this.mockMvc.perform(post("/api/orders/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getOrderController() throws Exception {
        String orderId = "111";

        this.mockMvc.perform(get("/api/orders/{orderId}", orderId))
                .andExpect(status().isOk());

    }

    @Test
    void getOrdersByDateController() throws Exception {
        this.mockMvc.perform(get("/api/orders")
                        .param("startDate", "20220515")
                        .param("endDate", "20220519"))
                .andExpect(status().isOk());

    }

}
