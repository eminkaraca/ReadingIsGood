package com.readingisgood.Service.Service.Interface;

import com.readingisgood.Service.Data.DTO.BookOrder;
import com.readingisgood.Service.Data.Entity.Book;
import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Request.PersistBookRequest;
import com.readingisgood.Service.Model.Response.Book.PersistBookResponse;
import com.readingisgood.Service.Model.Response.Book.UpdateStockResponse;

public interface IBookService {
    PersistBookResponse persist(PersistBookRequest bookInfo) throws RigException;

    UpdateStockResponse updateBookStock(String bookId, Integer stockCount);

    Book checkBookExist(BookOrder order);

    void save(Book book);
}
