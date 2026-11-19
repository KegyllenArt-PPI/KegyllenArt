package co.edu.poli.ppi.kegyllen.Kegyllen_app.service;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Usuario;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void vincularWallet(String walletAddress) {
        if (walletAddress == null || !walletAddress.matches("^0x[a-fA-F0-9]{40}$")) {
            throw new IllegalArgumentException("La dirección de wallet no cumple con el formato válido (0x + 40 caracteres hexadecimales).");
        }

        Usuario usuarioAutenticado = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuario = usuarioRepository.findById(usuarioAutenticado.getId_usuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        usuario.setWallet_address(walletAddress.toLowerCase());
        usuarioRepository.save(usuario);
    }
}