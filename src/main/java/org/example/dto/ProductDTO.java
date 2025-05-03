package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductDTO {

    @NotEmpty
    @Size(min = 2, max = 54, message = "Name should be between 2 and 54 characters")
    private String name;

    @NotNull(message = "Amount must not be null")
    @Min(2)
    @Max(999)
    private Short amount;
}
