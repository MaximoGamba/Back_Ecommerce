package com.ecomerce.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomerce.demo.Clases.Carrito;
import com.ecomerce.demo.Clases.Pedidos;
import com.ecomerce.demo.Clases.Producto;
import com.ecomerce.demo.Clases.ProductoCarrito;
import com.ecomerce.demo.Clases.ProductoPedido;
import com.ecomerce.demo.Clases.Usuarios;
import com.ecomerce.demo.Repositorys.CarritoRepository;
import com.ecomerce.demo.Repositorys.PedidosRepository;
import com.ecomerce.demo.Repositorys.ProductoRepository;
import com.ecomerce.demo.Repositorys.UsuariosRepository;
import com.ecomerce.demo.Request.PedidoRequest;
import com.ecomerce.demo.Response.CarritoProdResponse;
import com.ecomerce.demo.Response.CarritoResponse;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired 
    private ProductoRepository productoRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PedidosRepository pedidosRepository;

    @Transactional
    public CarritoResponse obtenerCarrito() {
        Usuarios usuario = authenticationService.obtenerUsuarioAutenticado();
        Carrito carrito = carritoRepository.findCarrito(usuario.getId());
        if (carrito == null) {
            carrito = new Carrito();
            usuario.setCarrito(carrito);
            usuariosRepository.save(usuario);
        }
        return mapearaCarritoResponse(carrito);
    }

    public CarritoResponse agregarProducto(long productId, int cantidad) {
        Usuarios usuario = authenticationService.obtenerUsuarioAutenticado();
        Carrito carrito = carritoRepository.findCarrito(usuario.getId());
        if (carrito == null){
            carrito = new Carrito();
            usuario.setCarrito(carrito);
            usuariosRepository.save(usuario);
            carrito = carritoRepository.save(carrito);
        }
        Producto producto = productoRepository.findById(productId);
        carrito.agregarProducto(producto, cantidad);
        carrito = carritoRepository.save(carrito);
        return mapearaCarritoResponse(carrito);
    }

    public CarritoResponse quitarProducto(long productId) {
        Usuarios usuario = authenticationService.obtenerUsuarioAutenticado();
        Carrito carrito = carritoRepository.findCarrito(usuario.getId());
        Producto producto = productoRepository.findById(productId);
        carrito.quitarProducto(producto);
        carritoRepository.save(carrito);
        return mapearaCarritoResponse(carrito);
    }

    public CarritoResponse vaciarCarrito() {
        Usuarios usuario = authenticationService.obtenerUsuarioAutenticado();
        Carrito carrito = carritoRepository.findCarrito(usuario.getId());
        carrito.vaciarCarrito();
        carritoRepository.save(carrito);
        return mapearaCarritoResponse(carrito);
    }

    public CarritoResponse modificarCantidadProducto(long productId, int nuevaCantidad){
        Usuarios usuario = authenticationService.obtenerUsuarioAutenticado();
        Carrito carrito = carritoRepository.findCarrito(usuario.getId());
        Producto producto = productoRepository.findById(productId);
        carrito.modificarCantidadProducto(producto, nuevaCantidad);
        carritoRepository.save(carrito);
        return mapearaCarritoResponse(carrito);
    }

    public CarritoResponse realizarPedido(PedidoRequest aux){
        Usuarios usuario = authenticationService.obtenerUsuarioAutenticado();
        Carrito carrito = carritoRepository.findCarrito(usuario.getId());
        if (carrito == null || carrito.getProductos().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío.");
        }
        Pedidos pedido = new Pedidos();
        pedido.setComprador(usuario);
        pedido.setPrecioTotal(carrito.getPrecioTotal());
        pedido.setDirreccion(aux.getDireccion());
        pedido.setMetodoPago(aux.getMetodoPago());
        List<ProductoPedido> productoPedidos = new ArrayList<>();
        Set<Long> vendedores = new HashSet<>();
        for (ProductoCarrito productoCarrito : carrito.getProductos()) {
            Producto producto = productoCarrito.getProducto();
            int cantidadComprada = productoCarrito.getCantidad();
            ProductoPedido proPedido = new ProductoPedido();
            proPedido.setProducto(producto);
            proPedido.setCantidad(cantidadComprada);
            productoPedidos.add(proPedido);
            Long userId = productoRepository.findUsuarioIdByProductoId(producto.getId());
            vendedores.add(userId);
            producto.setStock(producto.getStock() - cantidadComprada);
            productoRepository.save(producto);
        }
        pedido.setProductos(productoPedidos);
        pedido.setVendedores(vendedores);
        pedidosRepository.save(pedido);
        System.out.println("Paso el save producto");
        carrito.vaciarCarrito();
        System.out.println("Paso el vaciar producto");
        carritoRepository.save(carrito);
        System.out.println("Paso el guardar carrito");
        return mapearaCarritoResponse(carrito);
    }

    private CarritoResponse mapearaCarritoResponse(Carrito carrito){
        CarritoResponse carritoResponse = new CarritoResponse();
        carritoResponse.setPrecioTotal(carrito.getPrecioTotal());
        List<ProductoCarrito> lista = carrito.getProductos();
        List<CarritoProdResponse> auxiliar = new ArrayList<>();
        carritoResponse.setProductos(auxiliar);
        for (ProductoCarrito produc : lista){
            CarritoProdResponse carritoProdResponse = new CarritoProdResponse();
            carritoProdResponse.setCant(produc.getCantidad());
            carritoProdResponse.setProductId(produc.getProducto().getId());
            carritoProdResponse.setTitulo(produc.getProducto().getTitulo());
            carritoResponse.getProductos().add(carritoProdResponse);
        }
        return carritoResponse;
    }
}
