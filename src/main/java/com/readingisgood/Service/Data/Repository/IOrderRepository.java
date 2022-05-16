package com.readingisgood.Service.Data.Repository;


import com.readingisgood.Service.Data.Entity.Order;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

@JaversSpringDataAuditable
public interface IOrderRepository extends MongoRepository<Order,String> {

    Page<Order> findByCustomerId(String customerId, Pageable pageable);
    List<Order> findByInsertDateBetween(Integer startDate, Integer endDate);
}