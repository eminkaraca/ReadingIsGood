package com.readingisgood.Service.Data.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String name;

    private Double price;

    private Integer stock;

    @Builder.Default
    private Integer insertDate = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

    private Integer updateDate;
}
