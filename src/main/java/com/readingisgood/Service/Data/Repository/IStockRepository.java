package com.readingisgood.Service.Data.Repository;


import com.readingisgood.Service.Data.Entity.Stock;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

@JaversSpringDataAuditable
public interface IStockRepository extends MongoRepository<Stock,String> {

}