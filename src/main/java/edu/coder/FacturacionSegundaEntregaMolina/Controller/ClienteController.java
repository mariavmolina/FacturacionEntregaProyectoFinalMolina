package edu.coder.FacturacionSegundaEntregaMolina.Controller;

import edu.coder.FacturacionSegundaEntregaMolina.Model.Cliente;
import edu.coder.FacturacionSegundaEntregaMolina.DTO.ClienteDTO;
import edu.coder.FacturacionSegundaEntregaMolina.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Convierte un ClienteDTO en una entidad Cliente.
     *
     * @param clienteDTO El objeto DTO que representa al cliente.
     * @return Cliente La entidad Cliente correspondiente.
     */
    private Cliente convertToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.getId());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setDireccion(clienteDTO.getDireccion());
        return cliente;
    }
    /**
     * Agrega un nuevo cliente a través del servicio.
     *
     * @param clienteDTO El objeto DTO que representa al cliente a agregar.
     * @return ResponseEntity<Cliente> La respuesta HTTP con el cliente agregado.
     */

    @PostMapping("/agregar")
    public ResponseEntity<Cliente> agregarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = convertToEntity(clienteDTO);
        Cliente nuevoCliente = clienteService.agregarCliente(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    /**
     * Busca un cliente por su ID.
     *
     * @param id El ID del cliente a buscar.
     * @return ResponseEntity<Cliente> La respuesta HTTP con el cliente encontrado o un estado 404 si no existe.
     */

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable long id) {
        return clienteService.buscarCliente(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param id El ID del cliente a actualizar.
     * @param clienteDTO El objeto DTO que contiene la nueva información del cliente.
     * @return ResponseEntity<Cliente> La respuesta HTTP con el cliente actualizado o un estado 404 si no existe.
     */

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable long id, @RequestBody ClienteDTO clienteDTO) {
        Optional<Cliente> clienteExistente = clienteService.buscarCliente(id);
        if (clienteExistente.isPresent()) {
            Cliente cliente = convertToEntity(clienteDTO);
            cliente.setId(id); // Asegurar que el ID es el correcto
            Cliente clienteActualizado = clienteService.agregarCliente(cliente);
            return ResponseEntity.ok(clienteActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id El ID del cliente a eliminar.
     * @return ResponseEntity<Void> La respuesta HTTP con estado 204 si se eliminó con éxito, o estado 404 si no existe.
     */

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable long id) {
        if (clienteService.buscarCliente(id).isPresent()) {
            clienteService.eliminarCliente(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lista todos los clientes.
     *
     * @return ResponseEntity<Iterable<Cliente>> La respuesta HTTP con la lista de todos los clientes.
     */

    @GetMapping("/todos")
    public ResponseEntity<Iterable<Cliente>> listarClientes() {
        Iterable<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }
}
