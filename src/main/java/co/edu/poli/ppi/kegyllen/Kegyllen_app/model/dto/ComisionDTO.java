package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.Tipo_licencia;

import java.math.BigDecimal;

public class ComisionDTO {
    private Long id_cliente;
    private Long id_artista;
    private String respuestas_brief;
    private BigDecimal presupuesto_acordado;
    private Tipo_licencia tipo_licencia;
}
