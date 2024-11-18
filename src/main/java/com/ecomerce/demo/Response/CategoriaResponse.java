package com.ecomerce.demo.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponse {
    private long id;
    private String nombre;
    private List<ProductoResponse> catalogo;
}
