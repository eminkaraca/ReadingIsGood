package com.readingisgood.Service.Service.Interface;

import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Request.LoginRequest;
import com.readingisgood.Service.Model.Request.PersistCustomerRequest;
import com.readingisgood.Service.Model.Response.Customer.CustomerOrdersResponse;
import com.readingisgood.Service.Model.Response.Customer.PersistCustomerResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ICustomerService {

    PersistCustomerResponse persist(PersistCustomerRequest customerInfo) throws RigException;
    UserDetails loadUserByEmail(String email);
    UserDetails loadUserById(String id);
    String login(LoginRequest loginRequest);
}
