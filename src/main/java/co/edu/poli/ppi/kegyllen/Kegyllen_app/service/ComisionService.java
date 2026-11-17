package co.edu.poli.ppi.kegyllen.Kegyllen_app.service;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.ComisionReqDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.ComisionRespDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.*;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.repository.AsignacionComisionRepository;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.repository.ComisionRepository;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComisionService {
    private final ComisionRepository comisionRepository;
    private final AsignacionComisionRepository asignacionComisionRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public ComisionRespDTO crearComision(ComisionReqDTO request) {
        Usuario clienteAutenticado = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Long idCliente = clienteAutenticado.getId_usuario();
        Long idArtista = request.getId_usuario_artista();

        if (idCliente.equals(idArtista)) {
            throw new IllegalArgumentException("No puedes solicitar una comisión a ti mismo.");
        }

        if (!usuarioRepository.existsById(idArtista)) {
            throw new IllegalArgumentException("El artista seleccionado no existe.");
        }
        OffsetDateTime ahora = OffsetDateTime.now();

        Comision comision = new Comision();
        comision.setRespuestas_brief(request.getRespuestas_brief());
        comision.setPresupuesto_acordado(request.getPresupuesto_acordado());
        comision.setTipo_licencia(request.getTipo_licencia());
        comision.setEstado_comision(Estado_comision.SOLICITADA);
        comision.setFecha_creacion(ahora);
        comision.setFecha_actualizacion(ahora);
        Comision guardada = comisionRepository.save(comision);

        Asignaciones_comision asignacionCliente = new Asignaciones_comision();
        asignacionCliente.setComision(guardada);
        asignacionCliente.getId().setId_usuario(idCliente);
        asignacionCliente.setRol_comision(Rol_comision.CLIENTE);
        asignacionCliente.setFecha_asignacion(ahora);

        Asignaciones_comision asignacionArtista = new Asignaciones_comision();
        asignacionArtista.setComision(guardada);
        asignacionArtista.getId().setId_usuario(idArtista);
        asignacionArtista.setRol_comision(Rol_comision.ARTISTA);
        asignacionArtista.setFecha_asignacion(ahora);
        asignacionComisionRepository.saveAll(List.of(asignacionCliente, asignacionArtista));

        return new ComisionRespDTO(
                guardada.getId_comision(),
                guardada.getRespuestas_brief(),
                guardada.getPresupuesto_acordado(),
                guardada.getTipo_licencia(),
                guardada.getEstado_comision(),
                guardada.getFecha_creacion()
        );
    }
}
