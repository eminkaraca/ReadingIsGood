package com.readingisgood.Service.Model.Request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@Getter
@Setter
@Builder
@ApiModel(value = "PersistCustomerRequest")
public class PersistCustomerRequest {

    @NotBlank
    @NotNull
    @Email
    @ApiModelProperty(value = "Unique email field of Customer object - can not be null")
    private String email;


    @NotNull
    @NotBlank
    @ToString.Exclude
    @Size(min = 1,max = 15)
    @ApiModelProperty(value = "password of Customer object length range (1-15) can not be null")
    private String password;

    @NotBlank
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "name of Customer object max length 50")
    private String name;


    @NotBlank
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "Surname of Customer object max length 50")
    private String surname;

    @NotBlank
    @NotNull
    @Size(max = 250)
    @ApiModelProperty(value = "Open address of Customer object max length 250")
    private String openAddress;

    @NotBlank
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "Phone of Customer object max length 20")
    private String phone;

}
