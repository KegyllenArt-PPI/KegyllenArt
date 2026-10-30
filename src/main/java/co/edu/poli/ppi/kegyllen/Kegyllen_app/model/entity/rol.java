package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id_Rol;
    private String nombre;

}
