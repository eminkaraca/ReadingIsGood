package com.readingisgood.Service.Controller;

import com.readingisgood.Service.Data.Repository.ICustomerRepository;
import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Request.PersistCustomerRequest;
import com.readingisgood.Service.Model.Response.CustomerOrdersResponse;
import com.readingisgood.Service.Model.Response.PersistCustomerResponse;
import com.readingisgood.Service.Service.Interface.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomerService customerService;

    @PostMapping("/persist")
    public ResponseEntity<PersistCustomerResponse> persistCustomer(@RequestBody @Validated PersistCustomerRequest customerInfo){
        logger.info(String.format("persistCustomer Request : [%s]", customerInfo.toString()));
        try {
            PersistCustomerResponse newCustomer = customerService.persist(customerInfo);
            return new ResponseEntity<>(newCustomer, HttpStatus.OK);
        }catch (RigException re){
            logger.error("Customer Persist error ",re);
          //  return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
            throw  re;
        }catch (Exception e){
            logger.error("Customer Persist error ",e);
          //  return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }
    }


    @GetMapping("/{customerMail}/orders")
    public ResponseEntity<List<CustomerOrdersResponse>> getCustomerOrders(@PathVariable("customerMail") String customerMail,
                                                                          @PageableDefault(size = 20) Pageable pageable) {

        logger.info(String.format("getCustomerOrders for customer mail [%s]", customerMail));

        try {
            List<CustomerOrdersResponse> orders = customerService.getCustomerOrders(customerMail, pageable);

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
