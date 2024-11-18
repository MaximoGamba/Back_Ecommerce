package com.ecomerce.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomerce.demo.Clases.Pedidos;
import com.ecomerce.demo.Clases.ProductoPedido;
import com.ecomerce.demo.Clases.Usuarios;
import com.ecomerce.demo.Repositorys.PedidosRepository;
import com.ecomerce.demo.Response.CarritoProdResponse;
import com.ecomerce.demo.Response.PedidosResponse;
import com.ecomerce.demo.Response.UsuarioResponse;

import java.util.ArrayList;
import java.util.List;

@Service    
public class PedidosService {
    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public List<PedidosResponse> obtenerTodosPedidosUser (){
        Usuarios usuario = authenticationService.obtenerUsuarioAutenticado();
        List<Pedidos> pedidos = pedidosRepository.findByCompradorId(usuario.getId());
        return mapearListaPedidos(pedidos);
    }

    public List<PedidosResponse> obtenerTodosPedidosAdmin(){
        Usuarios usuario = authenticationService.obtenerUsuarioAutenticado();
        List<Pedidos> pedidos = pedidosRepository.findByVendedorId(usuario.getId());
        return mapearListaPedidosAdmin(pedidos, usuario.getId());
    }


    private List<PedidosResponse> mapearListaPedidos (List<Pedidos> pedidos){
        List<PedidosResponse> pedidosResponses = new ArrayList<>();
        for (Pedidos pe : pedidos){
            PedidosResponse pResponse = new PedidosResponse();
            pResponse.setId(pe.getId());
            pResponse.setDireccion(pe.getDirreccion());
            pResponse.setMetodoDePago(pe.getMetodoPago());
            pResponse.setPrecioTotal(pe.getPrecioTotal());
            UsuarioResponse usuarioResponse = new UsuarioResponse();
            usuarioResponse.setPrimerNombre(pe.getComprador().getFirstName());
            usuarioResponse.setApellido(pe.getComprador().getLastName());
            pResponse.setComprador(usuarioResponse);
            List<CarritoProdResponse> auxiliar = new ArrayList<>();
            List<ProductoPedido> lista = pe.getProductos();
            for (ProductoPedido produc : lista){
                CarritoProdResponse carritoProdResponse = new CarritoProdResponse();
                carritoProdResponse.setCant(produc.getCantidad());
                carritoProdResponse.setProductId(produc.getProducto().getId());
                carritoProdResponse.setTitulo(produc.getProducto().getTitulo());
                auxiliar.add(carritoProdResponse);
            }
            pResponse.setProductos(auxiliar);
            pedidosResponses.add(pResponse);
        }
        return pedidosResponses;
    }

    private List<PedidosResponse> mapearListaPedidosAdmin (List<Pedidos> pedidos, Long userId){
        List<PedidosResponse> pedidosResponses = new ArrayList<>();
        for (Pedidos pe : pedidos) {
            PedidosResponse pResponse = new PedidosResponse();
            pResponse.setId(pe.getId());
            pResponse.setDireccion(pe.getDirreccion());
            pResponse.setMetodoDePago(pe.getMetodoPago());
            pResponse.setPrecioTotal(pe.getPrecioTotal());
            UsuarioResponse usuarioResponse = new UsuarioResponse();
            usuarioResponse.setPrimerNombre(pe.getComprador().getFirstName());
            usuarioResponse.setApellido(pe.getComprador().getLastName());
            pResponse.setComprador(usuarioResponse);
            List<CarritoProdResponse> auxiliar = new ArrayList<>();
            List<ProductoPedido> lista = pe.getProductos();
            for (ProductoPedido produc : lista) {
                if (produc.getProducto().getUsuario().getId().equals(userId)) {
                    CarritoProdResponse carritoProdResponse = new CarritoProdResponse();
                    carritoProdResponse.setCant(produc.getCantidad());
                    carritoProdResponse.setProductId(produc.getProducto().getId());
                    carritoProdResponse.setTitulo(produc.getProducto().getTitulo());
                    auxiliar.add(carritoProdResponse);
                }
            }

            pResponse.setProductos(auxiliar);
            pedidosResponses.add(pResponse);
        }
        return pedidosResponses;
    }
}
