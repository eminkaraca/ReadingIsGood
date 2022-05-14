package com.readingisgood.Service.Controller;

import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Request.PersistBookRequest;
import com.readingisgood.Service.Model.Request.PersistCustomerRequest;
import com.readingisgood.Service.Model.Response.PersistBookResponse;
import com.readingisgood.Service.Model.Response.PersistCustomerResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);


    @PostMapping("/persist")
    public ResponseEntity<PersistBookResponse> persistBook(@RequestBody @Validated PersistBookRequest customerInfo){
        logger.info(String.format("persistCustomer Request : [%s]", customerInfo.toString()));
       /* try {
            PersistCustomerResponse newCustomer = customerService.persist(customerInfo);
            return new ResponseEntity<>(newCustomer, HttpStatus.OK);
        }catch (RigException re){
            logger.error("Customer Persist error ",re);
            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        }catch (Exception e){
            logger.error("Customer Persist error ",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
        return  null;
    }
}
