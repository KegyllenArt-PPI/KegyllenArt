package co.edu.poli.ppi.kegyllen.Kegyllen_app.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {
    // Clave secreta codificada en Base64
    @Value("${application.security.jwt.secret-key}")
    private String secret_key;

    // 1. Extraer el correo (subject) del token
    public String extractCorreo(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 2. Metodo genérico para extraer cualquier "claim" (información) del token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    // 3. Genera token sin claims adicionales (solo con el correo)
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("rol", userDetails.getAuthorities());

        return generateToken(extraClaims, userDetails);
    }
    // 4. Genera token con claims personalizados y el usuario
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        //extraClaims.put("rol", userDetails.getAuthorities());
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // El token expira en 24 horas (en milisegundos)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 5. Valida si el token pertenece al usuario y no ha expirado
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String correo = extractCorreo(token);
        return (correo.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    // 6. Comprueba si el token ha expirado
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    // Extraer la fecha de expiración
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    // Extraer todos los claims usando la clave secreta para verificar la firma
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Obtener la clave de firma decodificada
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
