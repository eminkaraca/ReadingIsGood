package com.readingisgood.Service.Model.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ApiModel(value = "LoginRequest")
public class LoginRequest {
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
}