package com.readingisgood.Service.Data.Repository;

import com.readingisgood.Service.Data.Entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ICustomerRepository extends MongoRepository<Customer,String> {

    Optional<Customer> findByEmail(String email);
}
