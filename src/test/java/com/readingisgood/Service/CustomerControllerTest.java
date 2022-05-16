package com.readingisgood.Service;

import com.google.gson.Gson;
import com.readingisgood.Service.Controller.BookController;
import com.readingisgood.Service.Controller.CustomerController;
import com.readingisgood.Service.Model.Request.LoginRequest;
import com.readingisgood.Service.Model.Request.PersistBookRequest;
import com.readingisgood.Service.Model.Request.PersistCustomerRequest;
import com.readingisgood.Service.Model.Response.Book.PersistBookResponse;
import com.readingisgood.Service.Model.Response.Customer.CustomerOrdersResponse;
import com.readingisgood.Service.Model.Response.Customer.LoginResponse;
import com.readingisgood.Service.Model.Response.Customer.PersistCustomerResponse;
import com.readingisgood.Service.Service.Interface.IBookService;
import com.readingisgood.Service.Service.Interface.ICustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    private static final String Email       = "emin.karaca@gmail.com";
    private static final String Password    = "1234";
    private static final String Name        = "Emin";
    private static final String Surname     = "Karaca";
    private static final String Address     = "Ankara";
    private static final String Phone       = "905330000000";

    @Autowired
    private CustomerController controller;

    @MockBean
    private ICustomerService service;

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
     * Create Customer
     */
    @Test
    public void createCustomerService(){

        PersistCustomerRequest newCustomerRequest = PersistCustomerRequest.builder()
                .email(Email)
                .password(Password)
                .name(Name)
                .surname(Surname)
                .openAddress(Address)
                .phone(Phone)
                .build();
        PersistCustomerResponse newCustomer =  PersistCustomerResponse.builder()
                .email(Email)
                .name(Name)
                .surname(Surname)
                .openAddress(Address)
                .phone(Phone)
                .build();

        when(service.persist(newCustomerRequest)).thenReturn(newCustomer);
    }
    @Test
    public void createCustomerController() throws Exception {

        PersistCustomerRequest newCustomerRequest = PersistCustomerRequest.builder()
                .email(Email)
                .password(Password)
                .name(Name)
                .surname(Surname)
                .openAddress(Address)
                .phone(Phone)
                .build();

        this.mockMvc.perform(post("/api/customers/persist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newCustomerRequest)))
                .andExpect(status().isCreated())
        ;
    }
    @Test
    public void createCustomerControllerInvalidEmail() throws Exception {

        PersistCustomerRequest newCustomerRequest = PersistCustomerRequest.builder()
                .email("")
                .password(Password)
                .name(Name)
                .surname(Surname)
                .openAddress(Address)
                .phone(Phone)
                .build();

        this.mockMvc.perform(post("/api/customers/persist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newCustomerRequest)))
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    public void createCustomerControllerInvalidPassword() throws Exception {

        PersistCustomerRequest newCustomerRequest = PersistCustomerRequest.builder()
                .email(Email)
                .password("")
                .name(Name)
                .surname(Surname)
                .openAddress(Address)
                .phone(Phone)
                .build();

        this.mockMvc.perform(post("/api/customers/persist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newCustomerRequest)))
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    public void createCustomerControllerInvalidName() throws Exception {

        PersistCustomerRequest newCustomerRequest = PersistCustomerRequest.builder()
                .email(Email)
                .password(Password)
                .name("")
                .surname(Surname)
                .openAddress(Address)
                .phone(Phone)
                .build();

        this.mockMvc.perform(post("/api/customers/persist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newCustomerRequest)))
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    public void createCustomerControllerInvalidSurName() throws Exception {

        PersistCustomerRequest newCustomerRequest = PersistCustomerRequest.builder()
                .email(Email)
                .password(Password)
                .name(Name)
                .surname("")
                .openAddress(Address)
                .phone(Phone)
                .build();

        this.mockMvc.perform(post("/api/customers/persist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newCustomerRequest)))
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    public void createCustomerControllerInvalidAddress() throws Exception {

        PersistCustomerRequest newCustomerRequest = PersistCustomerRequest.builder()
                .email(Email)
                .password(Password)
                .name(Name)
                .surname(Surname)
                .openAddress("")
                .phone(Phone)
                .build();

        this.mockMvc.perform(post("/api/customers/persist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newCustomerRequest)))
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    public void createCustomerControllerInvalidPhone() throws Exception {

        PersistCustomerRequest newCustomerRequest = PersistCustomerRequest.builder()
                .email(Email)
                .password(Password)
                .name(Name)
                .surname(Surname)
                .openAddress(Address)
                .phone("")
                .build();

        this.mockMvc.perform(post("/api/customers/persist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newCustomerRequest)))
                .andExpect(status().isBadRequest())
        ;
    }


    @Test
    public void loginCustomerController() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail(Email);
        request.setPassword(Password);


        this.mockMvc.perform(post("/api/customers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isOk())
        ;
    }
    @Test
    public void loginCustomerControllerIncorrectPassword() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail(Email);
        request.setPassword("");


        this.mockMvc.perform(post("/api/customers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void getCustomerController() throws Exception {

        this.mockMvc.perform(get("/api/customers/orders/{customerId}", "111")
                        .param("page", "0")
                        .param("size", "25"))
                        .andExpect(status().isNoContent());
    }
}
