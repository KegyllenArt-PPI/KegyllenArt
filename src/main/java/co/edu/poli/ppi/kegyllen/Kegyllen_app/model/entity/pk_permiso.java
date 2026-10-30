package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class pk_permiso {
    private short id_perfil;
    private int id_formulario;

    public pk_permiso() {
    }

    public pk_permiso(short id_perfil, int id_formulario) {
        this.id_perfil = id_perfil;
        this.id_formulario = id_formulario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        pk_permiso that = (pk_permiso) o;
        return Objects.equals(id_perfil, that.id_perfil) &&
                Objects.equals(id_formulario, that.id_formulario);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id_perfil, id_perfil);
    }
}

