package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    private String correo;
    private String username;
    private String celular;
    private String contrasenna;
    private String avatar;
    private String bibliografia;
    private String privi_did;
    private String wallet_address;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_usuario", nullable = false)
    private Estado_usuario estado;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "asignaciones_rol",
            inverseJoinColumns = @JoinColumn(name = "id_rol") ,
            joinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<Rol> roles = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Se reccoren los roles desde lo que se carga de la  BD
        return this.roles.stream()
                //Se transforma cada objeto 'Rol' en un 'SimpleGrantedAuthority', usando su nombre
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                //Se devuelven como lista que Spring Security puede leer
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.contrasenna;
    }

    @Override
    public String getUsername(){
        return this.correo;
    }

}

