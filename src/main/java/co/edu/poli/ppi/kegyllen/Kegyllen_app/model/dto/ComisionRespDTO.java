package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Estado_comision;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Tipo_licencia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComisionRespDTO {
    private Long id_comision;
    private String respuestas_brief;
    private BigDecimal presupuesto_acordado;
    private Tipo_licencia tipo_licencia;
    private Estado_comision estado_comision;
    private OffsetDateTime fecha_creacion;
}