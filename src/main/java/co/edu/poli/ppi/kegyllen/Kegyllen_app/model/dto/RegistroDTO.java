package co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDTO {
    private String username;
    private String correo;
    private String contrasenna;
    private String rol;
    private String celular;
}