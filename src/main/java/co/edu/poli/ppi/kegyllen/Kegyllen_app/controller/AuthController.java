package co.edu.poli.ppi.kegyllen.Kegyllen_app.controller;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.AuthDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.LoginDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.RegistroDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDTO> register(@RequestBody RegistroDTO request) {
        return ResponseEntity.ok(authService.registro(request));
    }

}
