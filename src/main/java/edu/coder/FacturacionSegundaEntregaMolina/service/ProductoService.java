package edu.coder.FacturacionSegundaEntregaMolina.service;

import edu.coder.FacturacionSegundaEntregaMolina.Model.Producto;
import edu.coder.FacturacionSegundaEntregaMolina.Exception.ProductoNotFoundException;
import edu.coder.FacturacionSegundaEntregaMolina.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;


/**
 * Servicio para la gestión de productos.
 * Proporciona métodos para agregar, buscar, actualizar, eliminar y listar productos.
 * También incluye validaciones para asegurar la integridad de los datos del producto.
 */
@Service
public class ProductoService {

    private static final Logger logger = Logger.getLogger(ProductoService.class.getName());

    @Autowired
    private ProductoRepository productoRepository;

    public Producto agregarProducto(Producto producto) {
        validarProducto(producto);
        try {
            return productoRepository.save(producto);
        } catch (Exception e) {
            logger.severe("Error al guardar el producto: " + e.getMessage());
            throw new RuntimeException("Error al guardar el producto.", e);
        }
    }

    public Optional<Producto> buscarProducto(Long id) {
        if (id == null || id <= 0) {
            logger.warning("ID inválido para búsqueda de producto.");
            throw new IllegalArgumentException("ID inválido.");
        }

        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isEmpty()) {
            logger.warning("Producto no encontrado con el ID: " + id);
            throw new ProductoNotFoundException(id);
        }
        return producto;
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        validarProducto(producto);
        if (!productoRepository.existsById(id)) {
            logger.warning("No se encontró producto con ID: " + id);
            throw new ProductoNotFoundException(id);
        }
        producto.setId(id);  // Aseguramos que el ID esté correcto
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            logger.warning("No se puede eliminar, producto no encontrado con ID: " + id);
            throw new ProductoNotFoundException(id);
        }
        productoRepository.deleteById(id);
    }

    public Iterable<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    private void validarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor a 0.");
        }
    }
}
