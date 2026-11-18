package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Tipo_licencia;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ComisionReqDTO {
    private Long id_usuario_artista;
    private String respuestas_brief;
    private BigDecimal presupuesto_acordado;
    private Tipo_licencia tipo_licencia;
}
