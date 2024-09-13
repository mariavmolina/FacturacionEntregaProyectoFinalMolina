package edu.coder.FacturacionSegundaEntregaMolina.Exception;

/**
 * Excepción personalizada que se lanza cuando no se encuentra un producto.
 * Esta excepción extiende de RuntimeException y se utiliza para indicar que un producto con el identificador especificado no fue encontrado.
 */
public class ProductoNotFoundException extends RuntimeException {

    public ProductoNotFoundException(Long id) {
        super("No se encontró el producto con id " + id); // Añadí un espacio para una mejor legibilidad
    }
}
