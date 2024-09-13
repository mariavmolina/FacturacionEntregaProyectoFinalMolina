// ComprobanteController.java
package edu.coder.FacturacionSegundaEntregaMolina.Controller;

import edu.coder.FacturacionSegundaEntregaMolina.DTO.ComprobanteDTO;
import edu.coder.FacturacionSegundaEntregaMolina.service.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comprobantes")
public class ComprobanteController {

    @Autowired
    private ComprobanteService comprobanteService;

    /**
     * Crea un nuevo comprobante utilizando la información proporcionada.
     *
     * @param comprobanteDTO El objeto DTO que contiene la información del comprobante a crear.
     * @return ResponseEntity<?> La respuesta HTTP con el comprobante creado o un mensaje de error en caso de fallo.
     */
    @PostMapping("/crear")
    public ResponseEntity<?> crearComprobante(@RequestBody ComprobanteDTO comprobanteDTO) {
        try {
            return ResponseEntity.ok(comprobanteService.crearComprobante(comprobanteDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
