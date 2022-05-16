package com.readingisgood.Service.Controller;

import com.readingisgood.Service.Exception.RigCustomeException;
import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Request.PersistBookRequest;
import com.readingisgood.Service.Model.Response.Book.PersistBookResponse;
import com.readingisgood.Service.Model.Response.Book.UpdateStockResponse;
import com.readingisgood.Service.Service.Interface.IBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
@Api(value = "Book Api documentation")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final IBookService bookService;

    /***
     * Adds a new book in to database
     * @param bookInfo {@link PersistBookRequest} book's informations
     * @return informations of new created book {@link PersistBookResponse}
     */
    @PostMapping("/new")
    @ApiOperation(value = "New Book adding method")
    public ResponseEntity<PersistBookResponse> newBook(@RequestBody @Valid PersistBookRequest bookInfo
                                                                        , BindingResult result)
    {
        logger.info(String.format("newBook Request : [%s]", bookInfo.toString()));
        if(result.hasErrors())
            throw new RigCustomeException(HttpStatus.BAD_REQUEST,"Incorrect Parameters");

        try {
            PersistBookResponse newBook = bookService.persist(bookInfo);
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        }catch (RigException re){
            logger.error("Book Persist error ",re);
            throw re;
        }catch (Exception e){
            logger.error("Book Persist error ",e);
            throw e;
        }
    }

    /***
     * Updates the stock of a book
     * @param bookId book id
     * @param stockCount new sock count
     * @return information of updated book {@link UpdateStockResponse}
     */
    @PutMapping("/update/{bookId}")
    @ApiOperation(value = "Updating stock of a book method")
    public ResponseEntity<UpdateStockResponse> updateBookStock(@PathVariable String bookId
                                                                , @RequestParam Integer stockCount)
    {
        logger.info(String.format("updateBookStock bookId, stock count [%s,%s]",bookId,stockCount.toString()));
        try {
            UpdateStockResponse updatedBook = bookService.updateBookStock(bookId, stockCount);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }catch (RigException re){
            logger.error("Book Persist error ",re);
            throw re;
        }catch (Exception e){
            logger.error("Book Persist error ",e);
            throw e;
        }
    }
}
