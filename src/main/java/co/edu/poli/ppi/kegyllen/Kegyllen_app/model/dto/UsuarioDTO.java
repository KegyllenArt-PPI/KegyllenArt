package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id_usuario;
    private String correo;
    private String username;
    private String celular;
    private String contrasenna;
    private String avatar;
    private String bibliografia;
    private String privi_did;
    private String wallet_address;
    private Short id_estado;
}
