package edu.coder.FacturacionSegundaEntregaMolina.Controller;

import edu.coder.FacturacionSegundaEntregaMolina.Model.Producto;
import edu.coder.FacturacionSegundaEntregaMolina.DTO.ProductoDTO;
import edu.coder.FacturacionSegundaEntregaMolina.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
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
        try {
            Producto producto = convertToEntity(productoDTO);
            Producto nuevoProducto = productoService.agregarProducto(producto);
            return ResponseEntity.ok(nuevoProducto); // Devuelve 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Devuelve 409 Conflict si ocurre un error
        }
    }

    /**
     * Busca un producto por su ID.
     *
     * @param id El ID del producto a buscar.
     * @return ResponseEntity<Producto> La respuesta HTTP con el producto encontrado o un estado 409 (Conflict) si no se encuentra.
     */
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Producto> buscarProducto(@PathVariable Long id) {
        return productoService.buscarProducto(id)
                .map(producto -> ResponseEntity.ok(producto)) // Devuelve 200 OK si el producto se encuentra
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build()); // Devuelve 409 Conflict si el producto no se encuentra
    }

    /**
     * Actualiza la información de un producto existente.
     *
     * @param id El ID del producto a actualizar.
     * @param productoDTO El DTO que contiene la nueva información del producto.
     * @return ResponseEntity<Producto> La respuesta HTTP con el producto actualizado o un estado 409 (Conflict) si el producto no existe.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        Optional<Producto> productoExistente = productoService.buscarProducto(id);
        if (productoExistente.isPresent()) {
            Producto producto = convertToEntity(productoDTO);
            producto.setId(id); // Asegurar que el ID es correcto
            Producto productoActualizado = productoService.agregarProducto(producto);
            return ResponseEntity.ok(productoActualizado); // Devuelve 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Devuelve 409 Conflict si el producto no existe
        }
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id El ID del producto a eliminar.
     * @return ResponseEntity<Void> La respuesta HTTP sin contenido si el producto fue eliminado o un estado 409 (Conflict) si el producto no existe.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoService.buscarProducto(id).isPresent()) {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content si el producto fue eliminado
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Devuelve 409 Conflict si el producto no existe
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
        return ResponseEntity.ok(productos); // Devuelve 200 OK
    }
}
