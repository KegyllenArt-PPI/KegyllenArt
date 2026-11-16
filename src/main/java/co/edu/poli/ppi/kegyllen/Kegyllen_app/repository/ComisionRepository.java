package co.edu.poli.ppi.kegyllen.Kegyllen_app.repository;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Comision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComisionRepository extends JpaRepository<Comision,Long> {
}
