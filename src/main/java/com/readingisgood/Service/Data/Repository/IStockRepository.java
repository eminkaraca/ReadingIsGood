package com.readingisgood.Service.Data.Repository;


import com.readingisgood.Service.Data.Entity.Stock;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

@JaversSpringDataAuditable
public interface IStockRepository extends MongoRepository<Stock,String> {

}