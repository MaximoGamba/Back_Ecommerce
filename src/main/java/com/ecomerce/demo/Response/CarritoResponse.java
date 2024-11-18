package com.ecomerce.demo.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoResponse {
    private List<CarritoProdResponse> productos;
    private Double precioTotal;
}
