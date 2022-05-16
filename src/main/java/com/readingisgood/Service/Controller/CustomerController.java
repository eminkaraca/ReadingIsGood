package com.readingisgood.Service.Controller;

import com.readingisgood.Service.Exception.RigCustomeException;
import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Request.LoginRequest;
import com.readingisgood.Service.Model.Request.PersistCustomerRequest;
import com.readingisgood.Service.Model.Response.Customer.CustomerOrdersResponse;
import com.readingisgood.Service.Model.Response.Customer.LoginResponse;
import com.readingisgood.Service.Model.Response.Customer.PersistCustomerResponse;
import com.readingisgood.Service.Service.Interface.ICustomerService;
import com.readingisgood.Service.Service.Interface.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Api(value = "Customer Api documentation")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomerService customerService;
    private final IOrderService orderService;

    /***
     * Insert a new customer in to database
     * @param customerInfo customer information {@link PersistCustomerRequest}
     * @return created customer information if success {@link PersistCustomerResponse}
     * @return  Exception if failure
     */
    @PostMapping("/persist")
    @ApiOperation(value = "New Customer adding method")
    public ResponseEntity<PersistCustomerResponse> persistCustomer(@RequestBody @Valid PersistCustomerRequest customerInfo
                                                                    , BindingResult result){
        logger.info(String.format("persistCustomer Request : [%s]", customerInfo.toString()));

        if(result.hasErrors())
            throw new RigCustomeException(HttpStatus.BAD_REQUEST,"Incorrect Parameters");

        try {
            PersistCustomerResponse newCustomer = customerService.persist(customerInfo);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        }catch (RigException re){
            logger.error("Customer Persist error ",re);
            throw  re;
        }catch (Exception e){
            logger.error("Customer Persist error ",e);
            throw e;
        }
    }

    /***
     * Returns a Customer's all orders by using paging
     * @param customerId id of the customer
     * @param pageable pageing information
     * @return returns the realted page of Order informations in a list of {@link CustomerOrdersResponse}
     */
    @GetMapping("/orders/{customerId}")
    @ApiOperation(value = "Query Customer's order method")
    public ResponseEntity<List<CustomerOrdersResponse>> getCustomerOrders(@PathVariable("customerId") String customerId,
                                                                          @PageableDefault(size = 20) Pageable pageable)
    {
        logger.info(String.format("getCustomerOrders for customer id [%s]", customerId));

        try {
            List<CustomerOrdersResponse> orders = orderService.getOrdersByCustomerId(customerId, pageable);

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /***
     * Login the use for Authentication
     * @param loginRequest {@link LoginRequest} user name and password information
     * @return a token that will be use for other request to authenticate
     */
    @PostMapping("/login")
    @ApiOperation(value = "Login Customer method for Authentication")
    public  ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest, BindingResult result){
        logger.info("login : [{}] ", loginRequest);

        if(result.hasErrors())
            throw new RigCustomeException(HttpStatus.BAD_REQUEST,"Incorrect Parameters");

        try {
            String token = customerService.login(loginRequest);
            return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
        }catch (RigException re){
            logger.error("Login error ",re);
            throw  re;
        }catch (Exception e){
            logger.error("login Exception",e);
            throw e;
        }
    }

}
