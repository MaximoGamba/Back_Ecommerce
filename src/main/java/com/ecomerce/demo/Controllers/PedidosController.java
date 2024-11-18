package com.ecomerce.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerce.demo.Request.PedidoRequest;
import com.ecomerce.demo.Response.CarritoResponse;
import com.ecomerce.demo.Response.PedidosResponse;
import com.ecomerce.demo.Services.CarritoService;
import com.ecomerce.demo.Services.PedidosService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;



@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("pedidos")
public class PedidosController {
    @Autowired
    private CarritoService carritoService;

    @Autowired
    private PedidosService pedidosService;

    @PostMapping("/realizar")
    public ResponseEntity<CarritoResponse> realizarPedido(@RequestBody PedidoRequest pedido) {
        try {
            CarritoResponse carritoResponse = carritoService.realizarPedido(pedido);
            return ResponseEntity.ok(carritoResponse);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/ObtenerTodosUser")
    public ResponseEntity<List<PedidosResponse>> obtenerPedidosUsuario() {
        return ResponseEntity.ok(pedidosService.obtenerTodosPedidosUser());
    }
    
    @GetMapping("/ObtenerTodosAdmin")
    public ResponseEntity<List<PedidosResponse>> obtenerPedidosAdmin() {
        return ResponseEntity.ok(pedidosService.obtenerTodosPedidosAdmin());
    }
}
