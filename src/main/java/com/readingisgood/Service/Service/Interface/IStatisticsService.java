package com.readingisgood.Service.Service.Interface;

import com.readingisgood.Service.Model.Response.Statistics.CustomerMonthlyOrderResponse;

import java.util.List;

public interface IStatisticsService {
    List<CustomerMonthlyOrderResponse> getCustomerMonthlyOrders(String customerId);
}
