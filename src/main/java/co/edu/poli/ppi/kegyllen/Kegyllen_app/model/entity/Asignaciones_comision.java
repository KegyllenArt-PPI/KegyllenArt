package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "ASIGNACIONES_COMISION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asignaciones_comision {
    @EmbeddedId
    private Asignacion_comision_Id id = new Asignacion_comision_Id();

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_comision", nullable = false)
    private Rol_comision rol_comision;

    private OffsetDateTime fecha_asignacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_comision")
    @JoinColumn(name = "id_comision")
    private Comision comision;

    @Column(name = "id_usuario", insertable = false, updatable = false)
    private Long id_usuario;

}
