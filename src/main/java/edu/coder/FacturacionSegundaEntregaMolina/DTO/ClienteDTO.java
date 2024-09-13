package edu.coder.FacturacionSegundaEntregaMolina.DTO;

/**
 * Data Transfer Object (DTO) para la entidad Cliente
 * Este DTO se utiliza para transferir datos de Cliente entre la capa de presentación y la capa de servicio.
 */
public class ClienteDTO {
    private long id;
    private String nombre;
    private String direccion;

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
