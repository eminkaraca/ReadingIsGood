package com.readingisgood.Service.Data.Entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@Builder
@Document("customers")
public class Customer {
    @Id
    private String id;
    private int insertDate;
    private int status;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String openAddress;
    private String phone;

}
