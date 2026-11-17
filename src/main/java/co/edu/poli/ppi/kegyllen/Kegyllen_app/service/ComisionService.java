package co.edu.poli.ppi.kegyllen.Kegyllen_app.service;

import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.ComisionReqDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.dto.ComisionRespDTO;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.model.entity.*;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.repository.AsignacionComisionRepository;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.repository.ComisionRepository;
import co.edu.poli.ppi.kegyllen.Kegyllen_app.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public List<ComisionRespDTO> listarComisionesUsuario() {
        Usuario usuarioAutenticado = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Long idUsuario = usuarioAutenticado.getId_usuario();

        List<Asignaciones_comision> asignaciones = asignacionComisionRepository.findByIdUsuario(idUsuario);

        return asignaciones.stream()
                .map(asignacion -> {
                    Comision c = asignacion.getComision();
                    return new ComisionRespDTO(
                            c.getId_comision(),
                            c.getRespuestas_brief(),
                            c.getPresupuesto_acordado(),
                            c.getTipo_licencia(),
                            c.getEstado_comision(),
                            c.getFecha_creacion()
                    );
                })
                .toList();
    }

    @Transactional
    public ComisionRespDTO actualizarEstadoComision(Long idComision, Estado_comision nuevoEstado, String txHash) {
        Usuario usuarioAutenticado = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Long idUsuario = usuarioAutenticado.getId_usuario();

        Comision comision = comisionRepository.findById(idComision)
                .orElseThrow(() -> new IllegalArgumentException("La comisión con ID " + idComision + " no existe."));

        Asignacion_comision_Id asignacionId = new Asignacion_comision_Id();
        asignacionId.setId_comision(idComision);
        asignacionId.setId_usuario(idUsuario);

        Asignaciones_comision asignacion = asignacionComisionRepository.findById(asignacionId)
                .orElseThrow(() -> new IllegalStateException("No tienes permisos o no participas en esta comisión."));

        Estado_comision estadoActual = comision.getEstado_comision();

        switch (nuevoEstado) {
            case ACEPTADA:
                if (asignacion.getRol_comision() != Rol_comision.ARTISTA) {
                    throw new IllegalStateException("Solo el artista asignado puede aceptar la comisión.");
                }
                if (estadoActual != Estado_comision.SOLICITADA) {
                    throw new IllegalStateException("Solo se puede aceptar una comisión en estado SOLICITADA.");
                }
                break;

            case EN_PROGRESO:
                if (asignacion.getRol_comision() != Rol_comision.ARTISTA) {
                    throw new IllegalStateException("Solo el artista puede iniciar el progreso.");
                }
                if (estadoActual != Estado_comision.ACEPTADA) {
                    throw new IllegalStateException("La comisión debe estar ACEPTADA para iniciar el progreso.");
                }
                break;

            case CANCELADA:
                if (estadoActual == Estado_comision.COMPLETADA) {
                    throw new IllegalStateException("No se puede cancelar una comisión que ya fue COMPLETADA.");
                }
                break;

            default:
                break;
        }

        comision.setEstado_comision(nuevoEstado);
        if (txHash != null && !txHash.isBlank()) {
            comision.setTx_hash_contrato(txHash);
        }
        comision.setFecha_actualizacion(OffsetDateTime.now());

        Comision actualizada = comisionRepository.save(comision);

        return new ComisionRespDTO(
                actualizada.getId_comision(),
                actualizada.getRespuestas_brief(),
                actualizada.getPresupuesto_acordado(),
                actualizada.getTipo_licencia(),
                actualizada.getEstado_comision(),
                actualizada.getFecha_creacion()
        );
    }
}
