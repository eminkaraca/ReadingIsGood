package com.readingisgood.Service.Service;

import com.readingisgood.Service.Data.DTO.BookOrder;
import com.readingisgood.Service.Data.Entity.Book;
import com.readingisgood.Service.Data.Repository.IBookRepository;
import com.readingisgood.Service.Exception.RigCustomeException;
import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Request.PersistBookRequest;
import com.readingisgood.Service.Model.Response.Book.PersistBookResponse;
import com.readingisgood.Service.Model.Response.Book.UpdateStockResponse;
import com.readingisgood.Service.Service.Interface.IBookService;
import lombok.RequiredArgsConstructor;
import org.javers.spring.annotation.JaversAuditable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService implements IBookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final IBookRepository bookRepository;

    /**
     * add new book
     * @param bookInfo {@link PersistBookRequest} book informations
     * @return {@link PersistBookResponse} created book informations
     * @throws RigException {@Link RigException}
     */
    @JaversAuditable
    @Override
    public PersistBookResponse persist(PersistBookRequest bookInfo) throws RigException {
        logger.info("adding new book [{}]",bookInfo);

        Book book = Book.builder()
                .name(bookInfo.getName())
                .price(bookInfo.getPrice())
                .stock(bookInfo.getStock())
                .build();

        Book newBook = bookRepository.save(book);

        return PersistBookResponse.fromBook(newBook);

    }

    /**
     * Update a book's stock nmber
     * @param bookId book id
     * @param stockCount new stok count
     * @return {@link UpdateStockResponse} updated book informations
     */
    @JaversAuditable
    @Override
    public UpdateStockResponse updateBookStock(String bookId, Integer stockCount) {
        logger.info(String.format("updateBookStock bookId, stock coun [%s,%s]",bookId,stockCount.toString()));

        if(stockCount < 0)
            throw new RigCustomeException(HttpStatus.NOT_ACCEPTABLE
                    ,String.format("Stock can not be negative [%s]",stockCount.toString()));

        Optional<Book> book = bookRepository.findById(bookId);

        if(!book.isPresent())
            throw new RigCustomeException(HttpStatus.NOT_FOUND,String.format("Can not find book with id [%s]",bookId));

        book.get().setStock(stockCount);

        bookRepository.save(book.get());

        return UpdateStockResponse.fromBook(book.get());
    }

    /**
     * checks if a book is exist and have an enough stock for an order
     * @param order order of that book {@link BookOrder}
     * @return returns the related book {@link Book}
     */
    @Override
    public Book checkBookExist(BookOrder order){
        logger.info("checking if book exists, bookId {}",order.getBookId());
        Optional<Book> book = bookRepository.findById(order.getBookId());
        if(book.isEmpty() )
            throw new RigCustomeException(HttpStatus.NOT_FOUND
                    ,String.format("Cannot find the book with id [%s]",order.getBookId()));
        if(book.get().getStock() < order.getCount() )
            throw new RigCustomeException(HttpStatus.NOT_ACCEPTABLE
                    ,String.format("Book with id [%s] do not have enough stock",order.getBookId()));
        return book.get();
    }

    /**
     * Saves a book
     * @param book book to save {@link Book}
     */
    @JaversAuditable
    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }
}
