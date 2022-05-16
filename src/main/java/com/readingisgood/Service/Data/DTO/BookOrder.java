package com.readingisgood.Service.Data.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BookOrder {
    @NotBlank
    @NotNull
    private String bookId;

    @Min(1)
    @NotNull
    private int count;
}
