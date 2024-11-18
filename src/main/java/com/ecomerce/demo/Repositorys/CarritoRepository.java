package com.ecomerce.demo.Repositorys;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecomerce.demo.Clases.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long>{

    @Query("SELECT u.carrito FROM Usuarios u WHERE u.id = :usuarioId")
    Carrito findCarrito(@Param("usuarioId") long usuarioId);
}
