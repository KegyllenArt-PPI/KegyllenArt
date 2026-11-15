package co.edu.poli.ppi.kegyllen.Kegyllen_app.config;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal
            (@NotNull HttpServletRequest request,
             @NotNull HttpServletResponse response,
             @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 1. Extrae la cabecera "Authorization" de la petición HTTP
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String correo;
        // 2. Si la cabecera no existe o no empieza con "Bearer ",
        // dejamos pasar la petición al siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        // 3. Extrae el token quitando los primeros 7 caracteres ("Bearer ")
        jwt = authHeader.substring(7);
        // 4. Extraer el correo del token usando JWTService
        correo = jwtService.extractCorreo(jwt);
        // 5. Si hay un correo y el usuario aún no está autenticado en el contexto de seguridad
        if(correo != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //Se cargan los datos del usuario desde la base de datos
            UserDetails userDetails= this.userDetailsService.loadUserByUsername(correo);
            // 6. Valida si el token es correcto para este usuario
            if (jwtService.isTokenValid(jwt,userDetails)){
                // Se crea el objeto de autenticación requerido por Spring Security
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // 7. Establece la autenticación en el SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 8. Continua con la cadena de filtros de Spring
        filterChain.doFilter(request,response);
    }
}
