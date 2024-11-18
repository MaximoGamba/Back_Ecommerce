package com.ecomerce.demo.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecomerce.demo.Clases.Pedidos;
import java.util.List;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long>{
    @Query("SELECT p FROM Pedidos p WHERE p.comprador.id = :usuarioId")
    List<Pedidos> findByCompradorId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT p FROM Pedidos p JOIN p.vendedores v WHERE v = :vendedorId")
    List<Pedidos> findByVendedorId(@Param("vendedorId") Long vendedorId);
}
