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
public class OrderBookResponse {


    private String customerId;

    private List<BookOrder> orders;

    private Integer totalBooks;

    private LocalDate orderDate;

    private Double totalPrice;

    private String status;

    public static OrderBookResponse fromOrder(Order order) {

        return OrderBookResponse.builder()
                .customerId(order.getCustomerId())
                .orders(order.getOrders())
                .totalBooks(order.getTotalBooks())
                .orderDate(Dater.getLocalDate(order.getInsertDate()))
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }
}
