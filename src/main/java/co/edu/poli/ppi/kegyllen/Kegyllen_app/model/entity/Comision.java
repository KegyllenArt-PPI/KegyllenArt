package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMISIONES")
public class Comision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comision;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String respuestas_brief;

    private BigDecimal presupuesto_acordado;
    @Enumerated(EnumType.STRING)
    private Tipo_licencia tipo_licencia;
    @Enumerated(EnumType.STRING)
    private Estado_comision estado_comision;
    private String tx_hash_contrato;
    private OffsetDateTime fecha_creacion;
    private OffsetDateTime fecha_actualizacion;
}


