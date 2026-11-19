package co.edu.poli.ppi.kegyllen.Kegyllen_app.controller;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.CertificadoEntregaReqDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.CertificadoRespDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.ComisionReqDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.ComisionRespDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Estado_comision;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.service.ComisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comisiones")
@RequiredArgsConstructor
public class ComisionController {
    private final ComisionService comisionService;
    @PostMapping
    public ResponseEntity<ComisionRespDTO> crearComision(@RequestBody ComisionReqDTO request) {
        ComisionRespDTO nuevaComision = comisionService.crearComision(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaComision);
    }

    @GetMapping("/mis-comisiones")
    public ResponseEntity<List<ComisionRespDTO>> listarMisComisiones() {
        List<ComisionRespDTO> comisiones = comisionService.listarComisionesUsuario();
        return ResponseEntity.ok(comisiones);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ComisionRespDTO> cambiarEstadoComision(
            @PathVariable Long id,
            @RequestParam Estado_comision nuevoEstado,
            @RequestParam(required = false) String txHash) {

        ComisionRespDTO comisionActualizada = comisionService.actualizarEstadoComision(id, nuevoEstado, txHash);
        return ResponseEntity.ok(comisionActualizada);
    }
}
