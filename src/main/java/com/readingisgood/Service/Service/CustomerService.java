package com.readingisgood.Service.Service;

import com.readingisgood.Service.Data.DTO.UserDetailsImpl;
import com.readingisgood.Service.Data.Entity.Customer;
import com.readingisgood.Service.Data.Repository.ICustomerRepository;
import com.readingisgood.Service.Exception.RigCustomeException;
import com.readingisgood.Service.Model.Request.LoginRequest;
import com.readingisgood.Service.Model.Request.PersistCustomerRequest;
import com.readingisgood.Service.Model.Response.Customer.CustomerOrdersResponse;
import com.readingisgood.Service.Model.Response.Customer.PersistCustomerResponse;
import com.readingisgood.Service.Security.Service.JWTService;
import com.readingisgood.Service.Service.Interface.ICustomerService;
import com.readingisgood.Service.Service.Interface.IOrderService;
import lombok.RequiredArgsConstructor;
import org.javers.spring.annotation.JaversAuditable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final ICustomerRepository customerRepository;
    private final JWTService jwtService;

    /***
     * Persist a new {@link Customer}
     * @param customerInfo customer information {@link PersistCustomerRequest}
     * @return newly persisted Customer as {@link PersistCustomerResponse}
     */
    @JaversAuditable
    @Override
    public PersistCustomerResponse persist(PersistCustomerRequest customerInfo) throws RigCustomeException {

        // Check if user already persisted
        Optional<Customer> customerOptional = customerRepository.findByEmail(customerInfo.getEmail());

        if (customerOptional.isPresent()) {
            throw new RigCustomeException(HttpStatus.NOT_ACCEPTABLE,"Customer already added");
        }

        Customer customer = Customer.builder()
                .email(customerInfo.getEmail())
                .password(customerInfo.getPassword())//Todo encode password
                .name(customerInfo.getName())
                .surname(customerInfo.getSurname())
                .openAddress(customerInfo.getOpenAddress())
                .phone(customerInfo.getPhone())
                .insertDate(Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))))
                .status(1)
                .build();

        customerRepository.save(customer);

        logger.info(String.format("Customer added email : [%s]", customerInfo.getEmail()));

        return PersistCustomerResponse.mapFromCustomer(customer);
    }

    @Override
    public UserDetails loadUserByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if(customer.isEmpty()){
            throw new RigCustomeException(HttpStatus.NOT_FOUND,"User not found");
        }

        return UserDetailsImpl.builder()
                .email(customer.get().getEmail())
                .password(customer.get().getPassword())
                .build();
    }

    /**
     * gets the user detail from user's id
     * @param id id of the user
     * @return user details {@link UserDetails}
     */
    @Override
    public UserDetails loadUserById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            throw new RigCustomeException(HttpStatus.NOT_FOUND,"User not found");
        }

        return UserDetailsImpl.builder()
                .email(customer.get().getEmail())
                .password(customer.get().getPassword())
                .build();
    }

    /**
     * Logins a user and create a JWT token
     * @param loginRequest login informations {@link LoginRequest}
     * @return  Token string
     */
    @Override
    public String login(LoginRequest loginRequest) {
        Optional<Customer> customer = customerRepository.findByEmail(loginRequest.getEmail());

        if(customer != null && customer.get().getPassword().equals(loginRequest.getPassword()))
            return jwtService.createJWT(loginRequest.getEmail());
        else
            throw new RigCustomeException(HttpStatus.NOT_ACCEPTABLE,"Customer email or password not valid!");
    }
}
