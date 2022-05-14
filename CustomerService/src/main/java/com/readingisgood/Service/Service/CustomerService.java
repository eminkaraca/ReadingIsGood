package com.readingisgood.Service.Service;

import com.readingisgood.Service.Data.Entity.Customer;
import com.readingisgood.Service.Data.Repository.ICustomerRepository;
import com.readingisgood.Service.Exception.PersistedBeforeException;
import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Request.PersistCustomerRequest;
import com.readingisgood.Service.Model.Response.CustomerOrdersResponse;
import com.readingisgood.Service.Model.Response.PersistCustomerResponse;
import com.readingisgood.Service.Service.Interface.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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


    /***
     * Persist a new {@Customer}
     * @param customerInfo customer information {@PersistCustomerRequest}
     * @return newly persisted Customer as {@PersistCustomerResponse}
     */
    public PersistCustomerResponse persist(PersistCustomerRequest customerInfo) throws PersistedBeforeException {

        // Check if user already persisted
        Optional<Customer> customerOptional = customerRepository.findByEmail(customerInfo.getEmail());

        if (customerOptional.isPresent()) {
            throw new PersistedBeforeException(HttpStatus.NOT_ACCEPTABLE,"Customer already added");
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
    public List<CustomerOrdersResponse> getCustomerOrders(String customerMail, Pageable pageable) {
        return null;
    }
}
