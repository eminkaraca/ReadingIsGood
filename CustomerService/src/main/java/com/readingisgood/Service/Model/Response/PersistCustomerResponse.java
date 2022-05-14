package com.readingisgood.Service.Model.Response;

import com.readingisgood.Service.Data.Entity.Customer;
import lombok.*;

@Data
@Getter
@Setter
@Builder
public class PersistCustomerResponse {

    private String email;
    private String name;
    private String surname;
    private String openAddress;
    private String phone;

    public static PersistCustomerResponse mapFromCustomer(Customer customer) {

        return PersistCustomerResponse.builder()
                .email(customer.getEmail())
                .name(customer.getName())
                .surname(customer.getSurname())
                .phone(customer.getPhone())
                .openAddress(customer.getOpenAddress())
                .build();

    }
}
