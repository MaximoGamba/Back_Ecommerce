package com.ecomerce.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecomerce.demo.Clases.Producto;
import com.ecomerce.demo.Exceptions.StockInsuficiente;
import com.ecomerce.demo.Repositorys.ProductoRepository;
import com.ecomerce.demo.Response.CarritoResponse;
import com.ecomerce.demo.Services.CarritoService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("carrito")
public class CarritoController {
    
    @Autowired
    private CarritoService carritoService;

    @Autowired 
    private ProductoRepository productoRepository;

    @GetMapping
    public ResponseEntity<CarritoResponse> obtenerCarrito() {
        return ResponseEntity.ok(carritoService.obtenerCarrito());
    }

    @PostMapping("/agregar")
    public ResponseEntity<CarritoResponse> agregarProducto(@RequestParam long productId, @RequestParam int cantidad) throws StockInsuficiente{
        Producto producto = productoRepository.findById(productId);
        if (producto.getStock() >= cantidad) {
            return ResponseEntity.ok(carritoService.agregarProducto(productId, cantidad));
        } else {
            throw new StockInsuficiente();
        }
    }

    @DeleteMapping("/quitar")
    public ResponseEntity<CarritoResponse> quitarProducto(@RequestParam long productId) {
        return ResponseEntity.ok(carritoService.quitarProducto(productId));
    }

    @DeleteMapping("/vaciar")
    public ResponseEntity<CarritoResponse> vaciarCarrito() {
        return ResponseEntity.ok(carritoService.vaciarCarrito());
    }

    @PutMapping("/modificarCantidad")
    public ResponseEntity<CarritoResponse> modificarCantidadProducto(@RequestParam long productId, @RequestParam int nuevaCantidad) throws StockInsuficiente {
        Producto producto = productoRepository.findById(productId);
        if (producto.getStock() >= nuevaCantidad) {
            return ResponseEntity.ok(carritoService.modificarCantidadProducto(productId, nuevaCantidad));
        } else {
            throw new StockInsuficiente();
        }
    }
    
}

