package com.readingisgood.Service.Model.Response.Statistics;

import com.readingisgood.Service.Model.Response.Customer.CustomerOrdersResponse;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;
import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@Builder
public class CustomerMonthlyOrderResponse {

    private String month;
    private Integer totalOrderCount;
    private Integer totalBookCount;
    private Double totalPurchasedAmount;

    public static CustomerMonthlyOrderResponse fromMap(Map.Entry<Month, List<CustomerOrdersResponse> > entry) {

        return CustomerMonthlyOrderResponse.builder()
                .month(entry.getKey().toString())
                .totalOrderCount(entry.getValue().size())
                .totalBookCount(entry.getValue().stream().map(CustomerOrdersResponse::getTotalBooks).mapToInt(Integer::intValue)
                        .sum())
                .totalPurchasedAmount(entry.getValue().stream().map(CustomerOrdersResponse::getTotalPrice)
                        .reduce(0.0, Double::sum))
                .build();
    }
}
