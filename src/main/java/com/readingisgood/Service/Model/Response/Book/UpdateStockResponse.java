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
public class UpdateStockResponse {

    private String id;
    private String bookName;
    private int stock;

    public static UpdateStockResponse fromBook(Book book) {

        return UpdateStockResponse.builder()
                .id(book.getId())
                .bookName(book.getName())
                .stock(book.getStock())
                .build();
    }
}
