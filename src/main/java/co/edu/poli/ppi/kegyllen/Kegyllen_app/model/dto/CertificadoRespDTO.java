package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CertificadoRespDTO {
    private Long id_certificado;
    private Long id_comision;
    private String ipfs_cid;
    private String hash_obra_sha256;
    private String tx_hash_sellado;
    private LocalDateTime fecha_sellado;
}