package com.readingisgood.Service.Model.Response.Book;

import com.readingisgood.Service.Data.Entity.Book;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class PersistBookResponse {

    private String id;
    private String bookName;
    private int stock;
    private double price;

    public static PersistBookResponse fromBook(Book book) {

        return PersistBookResponse.builder()
                .id(book.getId())
                .bookName(book.getName())
                .price(book.getPrice())
                .stock(book.getStock())
                .build();
    }
}
