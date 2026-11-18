package co.edu.poli.ppi.kegyllen.Kegyllen_app.repository;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Asignacion_comision_Id;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Asignaciones_comision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionComisionRepository extends JpaRepository <Asignaciones_comision, Asignacion_comision_Id> {
    @Query("SELECT a FROM Asignaciones_comision a WHERE a.id.id_usuario = :idUsuario")
    List<Asignaciones_comision> findByIdUsuario(@Param("idUsuario") Long idUsuario);
}
