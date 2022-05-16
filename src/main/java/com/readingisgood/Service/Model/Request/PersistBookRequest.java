package com.readingisgood.Service.Model.Request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@ApiModel(value = "PersistBookRequest")
public class PersistBookRequest {

    @NotBlank
    @NotNull
    @ApiModelProperty(value = "Name of the book")
    private String name;

    @NotNull
    @Positive
    @ApiModelProperty(value = "Price of the book")
    private Double price;

    @NotNull
    @Positive
    @Min(1)
    @ApiModelProperty(value = "Stock count of the book")
    private Integer stock;
}
