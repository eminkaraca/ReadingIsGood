package com.readingisgood.Service.Model.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@Getter
@Setter
public class PersistCustomerRequest {

    @NotBlank
    @NotNull
    @Email
    private String email;


    @NotNull
    @NotBlank
    @ToString.Exclude
    @Size(min = 1,max = 15)
    private String password;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String name;


    @NotBlank
    @NotNull
    @Size(max = 50)
    private String surname;

    @NotBlank
    @NotNull
    @Size(max = 250)
    private String openAddress;

    @NotBlank
    @NotNull
    @Size(max = 250)
    private String phone;

}
