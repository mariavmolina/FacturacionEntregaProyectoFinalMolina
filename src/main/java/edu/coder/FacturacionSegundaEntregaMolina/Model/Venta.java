package edu.coder.FacturacionSegundaEntregaMolina.Model;


import jakarta.persistence.*;

import java.util.List;


/**
 * Representa una entidad Venta en la base de datos.
 *
 * La clase Venta está mapeada a la tabla "venta" en la base de datos.
 * Incluye información sobre la venta, como el cliente que realizó la compra y los productos asociados a la venta.
 */
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "venta",cascade = CascadeType.ALL)
    private List<VentaProducto> ventaProductos;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<VentaProducto> getVentaProductos() {
        return ventaProductos;
    }

    public void setVentaProductos(List<VentaProducto> ventaProductos) {
        this.ventaProductos = ventaProductos;
    }
}
