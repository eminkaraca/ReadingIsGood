package com.readingisgood.Service.Data.Repository;

import com.readingisgood.Service.Data.Entity.Book;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.html.Option;

@JaversSpringDataAuditable
public interface IBookRepository extends MongoRepository<Book,String> {

}