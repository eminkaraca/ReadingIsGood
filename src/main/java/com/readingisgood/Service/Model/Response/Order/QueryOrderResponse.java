package com.readingisgood.Service.Model.Response.Order;

import com.readingisgood.Service.Data.DTO.BookOrder;
import com.readingisgood.Service.Data.Entity.Order;
import com.readingisgood.Service.Util.Dater;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@Builder
public class QueryOrderResponse {

    private String customerId;

    private List<BookOrder> orders;

    private Integer totalBooks;

    private Double totalPrice;

    private String status;

    private LocalDate insertDate;

    public static QueryOrderResponse mapFromOrder(Order order) {

        return QueryOrderResponse.builder()
                .customerId(order.getCustomerId())
                .orders(order.getOrders())
                .totalBooks(order.getTotalBooks())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .insertDate(Dater.getLocalDate(order.getInsertDate()))
                .build();

    }
}
