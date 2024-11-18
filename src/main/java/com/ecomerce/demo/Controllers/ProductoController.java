package com.ecomerce.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecomerce.demo.Response.ProductoResponse;
import com.ecomerce.demo.Services.ProductoService;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("productosUser")
@RequiredArgsConstructor
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/catalogo")
    public ResponseEntity<List<ProductoResponse>> obtenerTodosLosProductos() {
        List<ProductoResponse> productos = productoService.obtenerProductosPorUsuario();
        return ResponseEntity.ok(productos);
    }


    @GetMapping("/PorProducto")
    public ResponseEntity<ProductoResponse> obtenerProductoPorId(@RequestParam("id") long id) {
        ProductoResponse producto = productoService.obtenerProducto(id);
        return ResponseEntity.ok(producto);
    }
}