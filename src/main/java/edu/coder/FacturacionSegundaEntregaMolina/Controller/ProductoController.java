package edu.coder.FacturacionSegundaEntregaMolina.Controller;

import edu.coder.FacturacionSegundaEntregaMolina.Model.Producto;
import edu.coder.FacturacionSegundaEntregaMolina.DTO.ProductoDTO;
import edu.coder.FacturacionSegundaEntregaMolina.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * Convierte un DTO de Producto a una entidad Producto.
     *
     * @param productoDTO El DTO de Producto que se va a convertir.
     * @return La entidad Producto correspondiente.
     */
    private Producto convertToEntity(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        return producto;
    }

    /**
     * Agrega un nuevo producto a la base de datos.
     *
     * @param productoDTO El DTO que contiene la información del producto a agregar.
     * @return ResponseEntity<Producto> La respuesta HTTP con el producto agregado.
     */
    @PostMapping("/agregar")
    public ResponseEntity<Producto> agregarProducto(@RequestBody ProductoDTO productoDTO) {
        Producto producto = convertToEntity(productoDTO);
        Producto nuevoProducto = productoService.agregarProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    /**
     * Busca un producto por su ID.
     *
     * @param id El ID del producto a buscar.
     * @return ResponseEntity<Producto> La respuesta HTTP con el producto encontrado o un estado 404 (Not Found) si no se encuentra.
     */
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Producto> buscarProducto(@PathVariable Long id) {
        return productoService.buscarProducto(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualiza la información de un producto existente.
     *
     * @param id El ID del producto a actualizar.
     * @param productoDTO El DTO que contiene la nueva información del producto.
     * @return ResponseEntity<Producto> La respuesta HTTP con el producto actualizado o un estado 404 (Not Found) si el producto no existe.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        Optional<Producto> productoExistente = productoService.buscarProducto(id);
        if (productoExistente.isPresent()) {
            Producto producto = convertToEntity(productoDTO);
            producto.setId(id); // Asegurar que el ID es correcto
            Producto productoActualizado = productoService.agregarProducto(producto);
            return ResponseEntity.ok(productoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id El ID del producto a eliminar.
     * @return ResponseEntity<Void> La respuesta HTTP sin contenido si el producto fue eliminado o un estado 404 (Not Found) si el producto no existe.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoService.buscarProducto(id).isPresent()) {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lista todos los productos en la base de datos.
     *
     * @return ResponseEntity<Iterable<Producto>> La respuesta HTTP con una lista de todos los productos.
     */
    @GetMapping("/todos")
    public ResponseEntity<Iterable<Producto>> listarProductos() {
        Iterable<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }
}
