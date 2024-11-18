package com.ecomerce.demo.Clases;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "carrito_productos", joinColumns = @JoinColumn(name = "carrito_id"))
    private List<ProductoCarrito> productos = new ArrayList<>();

    private Double precioTotal = 0.0;

    @OneToOne(mappedBy = "carrito")
    private Usuarios usuario;

    public Carrito() {
        this.productos = new ArrayList<>();
        this.precioTotal = 0.0;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        ProductoCarrito productoCarrito = new ProductoCarrito(producto, cantidad);
        productos.add(productoCarrito);
        actualizarPrecioTotal();
    }

    public void quitarProducto(Producto producto) {
        productos.removeIf(p -> p.getProducto().equals(producto));
        actualizarPrecioTotal();
    }

    public void vaciarCarrito() {
        productos.clear();
        precioTotal = 0.0;
    }

    public void modificarCantidadProducto(Producto producto, int nuevaCantidad) {
        productos.stream()
            .filter(p -> p.getProducto().equals(producto))
            .findFirst()
            .ifPresent(p -> p.setCantidad(nuevaCantidad));
        actualizarPrecioTotal();
    }

    private void actualizarPrecioTotal() {
        precioTotal = productos.stream()
                .mapToDouble(p -> p.getProducto().PrecioDescuento() * p.getCantidad())
                .sum();
    }

}
