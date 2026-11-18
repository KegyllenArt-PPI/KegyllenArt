package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto;

import lombok.Data;

@Data
public class EntregaReqDTO {
    private String ipfs_cid;
    private String hash_obra_sha256;
    private String tx_hash_sellado;
}