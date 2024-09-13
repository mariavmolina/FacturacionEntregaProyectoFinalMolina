package edu.coder.FacturacionSegundaEntregaMolina.DTO;

/**
 * Data Transfer Object (DTO) para la entidad Producto.
 * Este DTO se utiliza para transferir datos de un producto entre la capa de presentación y la capa de servicio.
 */
public class ProductoDTO {
    private Long id;
    private String nombre;
    private Double precio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
