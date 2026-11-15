package co.edu.poli.ppi.kegyllen.Kegyllen_app.service;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.AuthDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.LoginDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.RegistroDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Rol;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Usuario;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Estado_usuario;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.repository.RolesRepository;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Lógica de Login
    public AuthDTO login(LoginDTO request) {
        // 1. Autenticar al usuario usando el AuthenticationManager de Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getContrasenna()
                )
        );

        // 2. Si las credenciales son correctas, buscamos al usuario en la BD
        var usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3. Validación opcional de seguridad: verificar estado
        if (usuario.getEstado() == Estado_usuario.BLOQUEADO ||
                usuario.getEstado() == Estado_usuario.INACTIVO) {
            throw new RuntimeException("Su cuenta se encuentra inactiva o bloqueada.");
        }

        // 3. Generamos el token JWT y lo devolvemos
        var jwtToken = jwtService.generateToken(usuario);
        return AuthDTO.builder()
                .token(jwtToken)
                .build();
    }

    //Lógica de Registro
    public AuthDTO registro(RegistroDTO request){
        String targetRole = "usuario"; // Rol por defecto si no envía nada

        if (request.getRol() != null && !request.getRol().isBlank()) {
            // Validación de seguridad opcional: evitar que se autoasignen ADMIN
            String requestedRole = request.getRol().toUpperCase();
            if (requestedRole.equals("ADMIN")) {
                throw new RuntimeException("No está permitido registrarse directamente como ADMIN");
            }
            targetRole = requestedRole;
        }
        // Buscamos el rol en la base de datos
        Rol role = rolesRepository.findByNombre(targetRole)
                .orElseThrow(() -> new RuntimeException("Error: El rol especificado no existe"));

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .correo(request.getCorreo())
                .celular(request.getCelular())
                .contrasenna(passwordEncoder.encode(request.getContrasenna()))
                .estado(Estado_usuario.ACTIVO)
                .roles(Set.of(role))
                .build();

        usuarioRepository.save(usuario);

        var jwtToken = jwtService.generateToken(usuario);
        return AuthDTO.builder().token(jwtToken).build();

    }
}