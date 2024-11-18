package com.ecomerce.demo.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecomerce.demo.Clases.Producto;

@Repository
public interface CatalogoRepository extends JpaRepository<Producto, Long>{
    
}
