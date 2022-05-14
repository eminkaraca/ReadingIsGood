package com.readingisgood.Service.Service.Interface;

import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Request.PersistCustomerRequest;
import com.readingisgood.Service.Model.Response.CustomerOrdersResponse;
import com.readingisgood.Service.Model.Response.PersistCustomerResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {

    PersistCustomerResponse persist(PersistCustomerRequest customerInfo) throws RigException;

    List<CustomerOrdersResponse> getCustomerOrders(String customerMail, Pageable pageable);
}
