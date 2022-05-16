package com.readingisgood.Service.Service;

import com.readingisgood.Service.Exception.RigCustomeException;
import com.readingisgood.Service.Model.Response.Customer.CustomerOrdersResponse;
import com.readingisgood.Service.Model.Response.Statistics.CustomerMonthlyOrderResponse;
import com.readingisgood.Service.Service.Interface.ICustomerService;
import com.readingisgood.Service.Service.Interface.IOrderService;
import com.readingisgood.Service.Service.Interface.IStatisticsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatisticsService implements IStatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsService.class);
    private final ICustomerService customerService;
    private final IOrderService orderService;

    @Override
    public List<CustomerMonthlyOrderResponse> getCustomerMonthlyOrders(String customerId) {

        List<CustomerOrdersResponse> orders = orderService.getOrdersByCustomerId(customerId, Pageable.unpaged());

        if(orders.isEmpty())
            throw new RigCustomeException(HttpStatus.NOT_FOUND
                    ,String.format("There is no statistics for customer id {}",customerId));

        Map<Month, List<CustomerOrdersResponse>> result = orders.stream()
                .collect(Collectors.groupingBy(order -> order.getOrderDate().getMonth()));

        return result.entrySet().stream()
                .map(CustomerMonthlyOrderResponse::fromMap)
                .collect(Collectors.toList());
    }

}
