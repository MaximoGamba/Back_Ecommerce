package com.ecomerce.demo.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidosResponse {
    private long id;
    private List<CarritoProdResponse> productos;
    private UsuarioResponse comprador;
    private double precioTotal;
    private String metodoDePago;
    private String direccion;
}
