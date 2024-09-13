package edu.coder.FacturacionSegundaEntregaMolina.Model;



import jakarta.persistence.*;

/**
 * Representa una entidad Producto en la base de datos.
 *
 * La clase Producto está mapeada a la tabla "producto" en la base de datos.
 * Incluye información sobre el producto, como su nombre, precio y stock disponible.
 */
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;

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
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
