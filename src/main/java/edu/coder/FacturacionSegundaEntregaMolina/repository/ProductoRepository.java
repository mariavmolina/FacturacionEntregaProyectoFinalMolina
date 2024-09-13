package edu.coder.FacturacionSegundaEntregaMolina.repository;

import edu.coder.FacturacionSegundaEntregaMolina.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
}
