package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class usuario {
    @Id
    private String correo;
    private String username;
    private String celular;
    private String contrasenna;
    private String avatar;
    private String bibliografia;
    private String privi_did;
    private String wallet_address;
    private short id_estado;
    private short id_perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false)
    private estado_usuario estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil", nullable = false)
    private perfil perfil;
}

