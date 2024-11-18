package com.ecomerce.demo.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoProdResponse {
    private long productId;
    private String titulo;
    private int cant;
}
