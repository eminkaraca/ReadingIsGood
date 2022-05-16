package com.readingisgood.Service.Service.Interface;

import com.readingisgood.Service.Model.Request.OrderBookRequest;
import com.readingisgood.Service.Model.Response.Customer.CustomerOrdersResponse;
import com.readingisgood.Service.Model.Response.Order.OrderBookResponse;
import com.readingisgood.Service.Model.Response.Order.QueryOrderResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    List<CustomerOrdersResponse> getOrdersByCustomerId(String customerId, Pageable pageable);

    OrderBookResponse persist(OrderBookRequest orderRequest);

    QueryOrderResponse getOrderById(String orderId);

    List<QueryOrderResponse> getOrdersByDate(String startDate, String endDate);
}
