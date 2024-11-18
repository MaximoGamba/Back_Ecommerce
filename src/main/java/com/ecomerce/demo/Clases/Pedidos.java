package com.ecomerce.demo.Clases;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection
    private List<ProductoPedido> productos;
    
    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private Usuarios comprador;

    @ElementCollection
    @CollectionTable(name = "pedido_vendedores", joinColumns = @JoinColumn(name = "pedido_id"))
    @Column(name = "vendedor_id")
    private Set<Long> vendedores;

    private double precioTotal;

    private String dirreccion;

    private String MetodoPago;
}
