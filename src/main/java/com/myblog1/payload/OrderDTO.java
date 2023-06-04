package com.myblog1.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;

    @NotNull(message = "Customer is required")
    private CustomerDTO customer;

    @NotEmpty(message = "Products are required")
    private List<ProductDTO> products;

    @NotNull(message = "Order total is required")
    @DecimalMin(value = "0.01", message = "Order total must be greater than or equal to 0.01")
    private BigDecimal total;

    @NotNull(message = "Order date and time is required")
    private LocalDateTime dateTime;
}


