package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "formularios")
public class formulario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_formulario;

    private String nombre_formulario;
    private char nodo_principal;
    private int dependencia;
    private int orden;
    private String icono;
    private String redirect;

}
