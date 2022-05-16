package com.readingisgood.Service;

import com.google.gson.Gson;
import com.readingisgood.Service.Controller.BookController;
import com.readingisgood.Service.Model.Request.PersistBookRequest;
import com.readingisgood.Service.Model.Response.Book.PersistBookResponse;
import com.readingisgood.Service.Model.Response.Book.UpdateStockResponse;
import com.readingisgood.Service.Service.Interface.IBookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest

@AutoConfigureMockMvc
public class BookControllerTest {

    private static final String BookName = "Ağaçlar ayakta Ölür test";
    private static final int Stock = 10;
    private static final double Price = 10.0;

    @Autowired
    private BookController controller;

    @MockBean
    private IBookService bookService;

    //@Autowired
    private MockMvc mockMvc;

    @Autowired
    protected Gson gson;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    /**
     * Create Book
     */
    @Test
    public void createBookService(){

        PersistBookRequest newBookRequest = PersistBookRequest.builder()
                                            .name(BookName)
                                            .stock(Stock)
                                            .price(Price)
                                            .build();
        PersistBookResponse newBook =  PersistBookResponse.builder()
                                        .bookName(BookName)
                                        .stock(Stock)
                                        .price(Price)
                                        .build();

        when(bookService.persist(newBookRequest)).thenReturn(newBook);
    }
    @Test
    public void createBookController() throws Exception {

        PersistBookRequest newBookRequest = PersistBookRequest.builder()
                .name(BookName)
                .stock(Stock)
                .price(Price)
                .build();

        this.mockMvc.perform(post("/api/book/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newBookRequest)))
                        .andExpect(status().isCreated())
                        ;

    }
    @Test
    public void createBookControllerInvalidName() throws Exception {

        PersistBookRequest newBookRequest = PersistBookRequest.builder()
                .name("")
                .stock(Stock)
                .price(Price)
                .build();

        this.mockMvc.perform(post("/api/book/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newBookRequest)))
                        .andExpect(status().isBadRequest());

    }
    @Test
    public void createBookControllerInvalidPrice() throws Exception {

        PersistBookRequest newBookRequest = PersistBookRequest.builder()
                .name(BookName)
                .stock(Stock)
                .price(-1.0)
                .build();

        this.mockMvc.perform(post("/api/book/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newBookRequest)))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void createBookControllerInvalidStock() throws Exception {

        PersistBookRequest newBookRequest = PersistBookRequest.builder()
                .name(BookName)
                .stock(0)
                .price(Price)
                .build();

        this.mockMvc.perform(post("/api/book/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newBookRequest)))
                .andExpect(status().isBadRequest());

    }


    /**
     * Update Book
     */
    @Test
    public void updateBookService(){

        UpdateStockResponse updatedBook =  UpdateStockResponse.builder()
                .bookName(BookName)
                .stock(Stock)
                .build();

        when(bookService.updateBookStock("111",Stock)).thenReturn(updatedBook);
    }
    @Test
    public void updateBookController() throws Exception {


        this.mockMvc.perform(put("/api/book/update/{bookId}","111")
                        .param("stockCount", String.valueOf(5)))
                        .andExpect(status().isOk())
        ;

    }

}
