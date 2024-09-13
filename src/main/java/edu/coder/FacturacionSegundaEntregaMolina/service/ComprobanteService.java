// ComprobanteService.java
package edu.coder.FacturacionSegundaEntregaMolina.service;

import edu.coder.FacturacionSegundaEntregaMolina.DTO.ComprobanteDTO;
import edu.coder.FacturacionSegundaEntregaMolina.Model.Cliente;
import edu.coder.FacturacionSegundaEntregaMolina.Model.Producto;
import edu.coder.FacturacionSegundaEntregaMolina.Model.Venta;
import edu.coder.FacturacionSegundaEntregaMolina.Model.VentaProducto;
import edu.coder.FacturacionSegundaEntregaMolina.repository.ClienteRepository;
import edu.coder.FacturacionSegundaEntregaMolina.repository.ProductoRepository;
import edu.coder.FacturacionSegundaEntregaMolina.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de comprobantes.
 * Proporciona métodos para crear un comprobante basado en un DTO.
 * Maneja validaciones de cliente y producto, así como la actualización de stock y creación de venta.
 */
@Service
public class ComprobanteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private VentaRepository ventaRepository;

    public Object crearComprobante(ComprobanteDTO comprobanteDTO) {
        // Validaciones
        Optional<Cliente> clienteOptional = clienteRepository.findById(comprobanteDTO.getCliente().getClienteid());
        if (!clienteOptional.isPresent()) {
            throw new RuntimeException("Cliente no existente.");
        }

        Venta venta = new Venta();
        venta.setCliente(clienteOptional.get());

        double total = 0.0;
        int cantidadTotal = 0;

        for (ComprobanteDTO.LineaDTO lineaDTO : comprobanteDTO.getLineas()) {
            Optional<Producto> productoOptional = productoRepository.findById(lineaDTO.getProducto().getProductoid());
            if (!productoOptional.isPresent()) {
                throw new RuntimeException("Producto no existente.");
            }

            Producto producto = productoOptional.get();
            if (lineaDTO.getCantidad() > producto.getStock()) {
                throw new RuntimeException("Cantidad solicitada mayor al stock.");
            }

            VentaProducto ventaProducto = new VentaProducto();
            ventaProducto.setProducto(producto);
            ventaProducto.setVenta(venta);
            ventaProducto.setCantidad(lineaDTO.getCantidad());


            producto.setStock(producto.getStock() - lineaDTO.getCantidad());
            productoRepository.save(producto);


            total += producto.getPrecio() * lineaDTO.getCantidad();
            cantidadTotal += lineaDTO.getCantidad();
        }


        RestTemplate restTemplate = new RestTemplate();
        String url = "http://worldclockapi.com/api/json/utc/now";
        String response = restTemplate.getForObject(url, String.class);
        String fecha = response != null ? response : new java.util.Date().toString();


        ventaRepository.save(venta);


        return new ComprobanteResponse(cantidadTotal, fecha, total);

    }


    public static class ComprobanteResponse {
        private String fecha;
        private double total;
        private int cantidadTotal;

        public ComprobanteResponse(int cantidadTotal, String fecha, double total) {
            this.cantidadTotal = cantidadTotal;
            this.fecha = fecha;
            this.total = total;
        }
    }

    public ClienteRepository getClienteRepository() {
        return clienteRepository;
    }

    public void setClienteRepository(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ProductoRepository getProductoRepository() {
        return productoRepository;
    }

    public void setProductoRepository(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public VentaRepository getVentaRepository() {
        return ventaRepository;
    }

    public void setVentaRepository(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }
}
