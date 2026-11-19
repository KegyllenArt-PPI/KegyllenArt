package co.edu.poli.ppi.kegyllen.Kegyllen_app.controller;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.WalletReqDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PatchMapping("/wallet")
    public ResponseEntity<Void> actualizarWallet(@RequestBody WalletReqDTO request) {
        usuarioService.vincularWallet(request.getWallet_address());
        return ResponseEntity.noContent().build();
    }
}
