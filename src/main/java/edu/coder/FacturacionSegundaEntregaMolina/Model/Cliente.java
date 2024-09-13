package edu.coder.FacturacionSegundaEntregaMolina.Model;

import jakarta.persistence.*;

import java.util.List;


/**
 * Representa una entidad Cliente en la base de datos.
 * La clase Cliente está mapeada a la tabla "cliente" en la base de datos.
 * Incluye información básica del cliente y su relación con las ventas que ha realizado.
 */
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL)
    private List<Venta> ventas;

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

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

}
