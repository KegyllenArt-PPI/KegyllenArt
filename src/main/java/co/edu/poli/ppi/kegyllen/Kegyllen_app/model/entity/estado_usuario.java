package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "estados_usuarios")
public class estado_usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id_estado;
    private String nombre;
}
