package co.edu.poli.ppi.kegyllen.Kegyllen_app.repository;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Certificado_entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificadoEntregaRepository extends JpaRepository<Certificado_entrega, Long> {
    @Query("SELECT c FROM Certificado_entrega c WHERE c.comision.id_comision = :idComision")
    Optional<Certificado_entrega> findByComisionIdComision(@Param("idComision") Long idComision);
    boolean existsByTxHashSellado(String txHashSellado);
}