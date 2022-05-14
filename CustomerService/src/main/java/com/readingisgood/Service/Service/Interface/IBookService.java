package com.readingisgood.Service.Service.Interface;

import com.readingisgood.Service.Exception.RigException;
import com.readingisgood.Service.Model.Request.PersistBookRequest;
import com.readingisgood.Service.Model.Request.PersistCustomerRequest;
import com.readingisgood.Service.Model.Response.PersistBookResponse;
import com.readingisgood.Service.Model.Response.PersistCustomerResponse;

public interface IBookService {
    PersistBookResponse persist(PersistBookRequest bookInfo) throws RigException;
}
