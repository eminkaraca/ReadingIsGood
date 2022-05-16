package com.readingisgood.Service.Data.Repository;

import com.readingisgood.Service.Data.Entity.Book;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;


@JaversSpringDataAuditable
public interface IBookRepository extends MongoRepository<Book,String> {

}