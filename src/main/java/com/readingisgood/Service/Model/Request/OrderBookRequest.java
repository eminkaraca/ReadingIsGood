package com.readingisgood.Service.Model.Request;

import com.readingisgood.Service.Data.DTO.BookOrder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value = "OrderBookRequest")
public class OrderBookRequest {

    @NotNull
    @NotBlank
    @Size(min = 24,max = 24)
    @ApiModelProperty(value = "Customer Id fix length 24")
    private String customerId;

    @NotNull
    @ApiModelProperty(value = "List of BookOrder")
    private List<BookOrder> orders;

}
