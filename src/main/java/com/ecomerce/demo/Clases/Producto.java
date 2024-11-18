package com.ecomerce.demo.Clases;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

// Importante, guardar URL de img y videos o guardar blob en BD directamente.

@Data
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;
    private Double precio;
    private String descripcion;
    private int stock;

    private Double descuento= 0.0;
    private Boolean EstadoDescuento= false;

    @Lob
    private byte[] imagenes;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;

    @ManyToMany
    @JoinTable(
        name = "producto_categoria",
        joinColumns = @JoinColumn(name = "producto_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categorias> categorias;

    public Double PrecioDescuento(){
        if (EstadoDescuento)
            return precio - (precio*(descuento/100));
        return precio;
    }
    
    public void ActivarDescuento(Double p){
        this.descuento = p;
        this.EstadoDescuento = true;
    }

    public void DesactivarDescuento(){
        this.descuento = 0.0;
        this.EstadoDescuento = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; 
        if (o == null || getClass() != o.getClass()) return false; 
        Producto producto = (Producto) o;
        return id == producto.id; 
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); 
    }
}
