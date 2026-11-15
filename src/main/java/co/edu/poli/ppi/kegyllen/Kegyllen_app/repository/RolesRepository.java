package co.edu.poli.ppi.kegyllen.Kegyllen_app.repository;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Rol,Short> {
    Optional<Rol> findByNombre(String nombre);
}
