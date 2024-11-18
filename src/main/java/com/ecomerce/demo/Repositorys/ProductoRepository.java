package com.ecomerce.demo.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecomerce.demo.Clases.Categorias;
import com.ecomerce.demo.Clases.Producto;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Producto findById(long id);

    @Query("SELECT p FROM Producto p WHERE p.stock >= 1")
    List<Producto> findAllWithStock();

    @Query("SELECT p FROM Producto p WHERE p.usuario.id = :adminId")
    List<Producto> findAllByAdminId(Long adminId);

    @Query("SELECT c.id FROM Producto p JOIN p.categorias c WHERE p.id = :productoId")
    List<Long> findCategoriaIdsByProductoId(@Param("productoId") Long productoId);
    
    List<Producto> findByCategoriasIn(Set<Categorias>categorias);

    @Query("SELECT p.usuario.id FROM Producto p WHERE p.id = :productoId")
    Long findUsuarioIdByProductoId(@Param("productoId") long productoId);

}
