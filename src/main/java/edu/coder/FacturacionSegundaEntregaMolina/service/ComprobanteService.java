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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ComprobanteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private VentaRepository ventaRepository;

    public ResponseEntity<ComprobanteResponse> crearComprobante(ComprobanteDTO comprobanteDTO) {
        // Validaciones
        Optional<Cliente> clienteOptional = clienteRepository.findById(comprobanteDTO.getCliente().getClienteid());
        if (!clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ComprobanteResponse("Cliente no existente.", "", 0, 0));
        }

        Venta venta = new Venta();
        venta.setCliente(clienteOptional.get());

        double total = 0.0;
        int cantidadTotal = 0;

        for (ComprobanteDTO.LineaDTO lineaDTO : comprobanteDTO.getLineas()) {
            Optional<Producto> productoOptional = productoRepository.findById(lineaDTO.getProducto().getProductoid());
            if (!productoOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ComprobanteResponse("Producto no existente.", "", 0, 0));
            }

            Producto producto = productoOptional.get();
            if (lineaDTO.getCantidad() > producto.getStock()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ComprobanteResponse("Cantidad solicitada mayor al stock.", "", 0, 0));
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

        ComprobanteResponse comprobanteResponse = new ComprobanteResponse(fecha, total, cantidadTotal);
        return ResponseEntity.ok(comprobanteResponse);
    }

    public static class ComprobanteResponse {
        private String mensaje;
        private String fecha;
        private double total;
        private int cantidadTotal;

        public ComprobanteResponse(String mensaje, String fecha, double total, int cantidadTotal) {
            this.mensaje = mensaje;
            this.fecha = fecha;
            this.total = total;
            this.cantidadTotal = cantidadTotal;
        }

        public ComprobanteResponse(String fecha, double total, int cantidadTotal) {
            this.mensaje = "";
            this.fecha = fecha;
            this.total = total;
            this.cantidadTotal = cantidadTotal;
        }

        // Getters y setters
        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public int getCantidadTotal() {
            return cantidadTotal;
        }

        public void setCantidadTotal(int cantidadTotal) {
            this.cantidadTotal = cantidadTotal;
        }
    }
}
