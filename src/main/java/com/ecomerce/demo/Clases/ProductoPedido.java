package com.ecomerce.demo.Clases;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class ProductoPedido {
    @ManyToOne
    private Producto producto;

    private int cantidad;

    public ProductoPedido() {}

    public ProductoPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
}
