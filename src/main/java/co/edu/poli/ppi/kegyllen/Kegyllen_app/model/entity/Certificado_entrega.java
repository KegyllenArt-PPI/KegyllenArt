package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "CERTIFICADOS_ENTREGA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificado_entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCertificado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comision", referencedColumnName = "id_comision", nullable = false, unique = true)
    private Comision comision;
    private String ipfsCid;
    private String hashObraSha256;
    @Column(unique = true, length = 66)
    private String txHashSellado;
    private LocalDateTime fechaSellado = LocalDateTime.now();
}