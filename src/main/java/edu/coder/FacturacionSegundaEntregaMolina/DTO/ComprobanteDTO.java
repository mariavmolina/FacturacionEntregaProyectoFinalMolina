package edu.coder.FacturacionSegundaEntregaMolina.DTO;

import java.util.List;

/**
 * Data Transfer Object (DTO) para la entidad Comprobante.
 * Este DTO se utiliza para transferir datos de comprobante entre la capa de presentación y la capa de servicio.
 */
public class ComprobanteDTO {
    private ClienteDTO cliente;
    private List<LineaDTO> lineas;

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<LineaDTO> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaDTO> lineas) {
        this.lineas = lineas;
    }

    public static class ClienteDTO{
        private Long clienteid;

        public Long getClienteid() {
            return clienteid;
        }

        public void setClienteid(Long clienteid) {
            this.clienteid = clienteid;
        }
    }

    public static class LineaDTO{
        private Integer cantidad;
        private ProductoDTO producto;

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public ProductoDTO getProducto() {
            return producto;
        }

        public void setProducto(ProductoDTO producto) {
            this.producto = producto;
        }

        public static class ProductoDTO{
            private Long productoid;

            public Long getProductoid() {
                return productoid;
            }

            public void setProductoid(Long productoid) {
                this.productoid = productoid;
            }
        }
    }


}

