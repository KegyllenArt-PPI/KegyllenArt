package co.edu.poli.ppi.kegyllen.Kegyllen_app.controller;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.ComisionReqDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.ComisionRespDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.service.ComisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
