package co.edu.poli.ppi.kegyllen.Kegyllen_app.repository;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario,Long> {
    Optional<Usuario> findByCorreo(String correo);
}
