package co.edu.poli.ppi.kegyllen.Kegyllen_app.repository;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Asignacion_comision_Id;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Asignaciones_comision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionComisionRepository extends JpaRepository <Asignaciones_comision, Asignacion_comision_Id> {
}
