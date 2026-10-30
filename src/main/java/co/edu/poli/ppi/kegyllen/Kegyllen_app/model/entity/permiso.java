package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permisos")
public class permiso {

    @EmbeddedId
    private pk_permiso id_permiso;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_perfil")
    @JoinColumn(name = "id_perfil")
    private perfil perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_formulario")
    @JoinColumn(name = "id_formulario")
    private formulario formulario;

    private char crear;
    private char editar;
    private char leer;
    private char eliminar;

}


