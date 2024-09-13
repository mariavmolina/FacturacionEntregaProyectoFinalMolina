package edu.coder.FacturacionSegundaEntregaMolina.service;

import edu.coder.FacturacionSegundaEntregaMolina.Model.Cliente;
import edu.coder.FacturacionSegundaEntregaMolina.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;


/**
 * Servicio para manejar la lógica de negocio relacionada con los clientes.
 *
 * Proporciona métodos para agregar, buscar, actualizar, eliminar y listar clientes.
 * Utiliza el repositorio ClienteRepository para interactuar con la base de datos.
 */
@Service
public class ClienteService {

    private static final Logger logger = Logger.getLogger(ClienteService.class.getName());

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente agregarCliente(Cliente cliente) {
        validarCliente(cliente);
        try {
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            logger.severe("Error al guardar el cliente: " + e.getMessage());
            throw new RuntimeException("Error al guardar el cliente.", e);
        }
    }

    public Optional<Cliente> buscarCliente(Long id) {
        if (id == null || id <= 0) {
            logger.warning("ID inválido para búsqueda de cliente.");
            throw new IllegalArgumentException("ID inválido.");
        }

        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            logger.warning("Cliente no encontrado con el ID: " + id);
        }
        return cliente;
    }

    @Transactional
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        validarCliente(cliente);
        if (!clienteRepository.existsById(id)) {
            logger.warning("No se encontró cliente con ID: " + id);
            throw new IllegalArgumentException("No se encontró cliente con ese ID.");
        }
        cliente.setId(id);  // Aseguramos que el ID esté correcto
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminarCliente(long id) {
        if (!clienteRepository.existsById(id)) {
            logger.warning("No se puede eliminar, cliente no encontrado con ID: " + id);
            throw new IllegalArgumentException("No se puede eliminar, cliente no encontrado.");
        }
        clienteRepository.deleteById(id);
    }

    public Iterable<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    private void validarCliente(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }
        if (cliente.getDireccion() == null || cliente.getDireccion().isEmpty()) {
            throw new IllegalArgumentException("La dirección del cliente es obligatoria.");
        }
    }
}
