package com.ecomerce.demo.Controllers;


import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerce.demo.Services.CategoriasService;
import com.ecomerce.demo.Clases.Categorias;
import com.ecomerce.demo.Exceptions.CategoriasDuplicateException;
import com.ecomerce.demo.Request.CategoriaRequest;
import com.ecomerce.demo.Response.CategoriaResponse;
import com.ecomerce.demo.Response.ProductoResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("categorias")
public class CategoriaCotroller {
    @Autowired
    private CategoriasService categoriasService;

    @GetMapping("/ObtenerCategorias")
    public ResponseEntity<List<CategoriaResponse>> getCategorias(){
        return ResponseEntity.ok(categoriasService.obtenerTodas());
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<ProductoResponse>> filtro(
            @RequestParam List<Long> nombres ){
        return ResponseEntity.ok(categoriasService.filtrado(nombres));
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/CrearCat")
    public ResponseEntity<Object> createCategory(@RequestBody CategoriaRequest categoriaRequest)
            throws CategoriasDuplicateException {
        Categorias result = categoriasService.createCategoria(categoriaRequest.getNombre());
        return ResponseEntity.created(URI.create("/categories/" + result.getId())).body(result);
    }
}
