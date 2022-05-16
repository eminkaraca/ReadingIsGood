package com.readingisgood.Service.Data.Entity;

import com.readingisgood.Service.Data.DTO.BookOrder;
import com.readingisgood.Service.Model.Request.OrderBookRequest;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@Data
@Builder
@Document("orders")
public class Order {

    @Id
    private String id;

    private String customerId;

    private List<BookOrder> orders;

    private Integer totalBooks;

    private Double totalPrice;

    private String status;

    @Builder.Default
    private Integer insertDate = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));


}
