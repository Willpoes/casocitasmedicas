package com.example.Pacientes.service;

import com.example.Pacientes.dto.PacienteDTO;
import com.example.Pacientes.dto.PacienteDetalleDTO;
import com.example.Pacientes.entity.PacienteEntity;
import com.example.Pacientes.exception.BadRequestException;
import com.example.Pacientes.exception.ResourceNotFoundException;
import com.example.Pacientes.reposity.PacienteReposity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final PacienteReposity pacienteReposity;

    public PacienteService(PacienteReposity pacienteReposity) {
        this.pacienteReposity = pacienteReposity;
    }

    // 🔹 Listar pacientes -> Devuelvo DTOs
    public List<PacienteDTO> listarPacientes(){
        return pacienteReposity.findAll()
                .stream() //convierte lista a un stram
                .map(this::convertirEntityADTO)
                .collect(Collectors.toList());
    }

    // 🔹 Obtener por ID -> Devuelvo DTO
    public PacienteDTO obtenerPacientePorId(Long id){
        PacienteEntity entity = pacienteReposity.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente con ID " + id + " no encontrado"));
        return convertirEntityADTO(entity);
    }

    public PacienteDetalleDTO obtenerPacienteDetalle(Long id) {
        PacienteEntity entity = pacienteReposity.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente con ID " + id + " no encontrado"));

        return new PacienteDetalleDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getEdad(),
                entity.getHistorial(),
                entity.getTipo_sangre() // aquí ya sí mandamos el historial
        );
    }


    // 🔹 Crear Paciente -> Recibo DTO y devuelvo DTO
    public PacienteDTO guardarPaciente(PacienteDTO pacienteDTO){
        PacienteEntity entity = convertirDTOAEntity(pacienteDTO);
        validar(entity);
        PacienteEntity guardado = pacienteReposity.save(entity);
        return convertirEntityADTO(guardado);
    }

    // 🔹 Actualizar historial de un paciente
    public PacienteDetalleDTO actualizarHistorial(Long id, String nuevoHistorial) {
        PacienteEntity entity = pacienteReposity.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente con ID " + id + " no existe"));

        entity.setHistorial(nuevoHistorial);

        PacienteEntity actualizado = pacienteReposity.save(entity);
        return new PacienteDetalleDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getEdad(),
                entity.getHistorial(),
                entity.getTipo_sangre()
        );
    }


    // 🔹 Actualizar Paciente -> Recibo DTO y devuelvo DTO
    public PacienteDTO cambiarPaciente(Long id, PacienteDTO pacienteDTO){
        PacienteEntity entity = convertirDTOAEntity(pacienteDTO);
        validar(entity);

        PacienteEntity pacienteExistente = pacienteReposity.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar, el paciente con ID " + id + " no existe"));

        pacienteExistente.setNombre(entity.getNombre());
        pacienteExistente.setEdad(entity.getEdad());
        //pacienteExistente.setHistorial(entity.getHistorial());
        pacienteExistente.setTipo_sangre(entity.getTipo_sangre());

        PacienteEntity actualizado = pacienteReposity.save(pacienteExistente);
        return convertirEntityADTO(actualizado);
    }





    // 🔹 Eliminar paciente
    public void eliminarPaciente(Long id){
        if (!pacienteReposity.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, el paciente con ID " + id + " no existe");
        }
        pacienteReposity.deleteById(id);
    }


    private PacienteDTO convertirEntityADTO(PacienteEntity entity) {
        return new PacienteDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getEdad(),
                entity.getTipo_sangre()
        );
    }

    private PacienteEntity convertirDTOAEntity(PacienteDTO dto) {
        return new PacienteEntity(
                dto.getNombre(),
                dto.getEdad(),
                "Sin historial",  // si tu DTO no lo incluye todavía
                dto.getTipoSangre()
        );
    }

    private void validar(PacienteEntity paciente) {
        if (paciente.getNombre() == null || paciente.getNombre().isBlank()){
            throw new BadRequestException("El nombre no puede estar vacío");
        }
        if (paciente.getEdad() < 0){
            throw new BadRequestException("La edad no puede ser negativa");
        }
//        if (paciente.getHistorial() == null || paciente.getHistorial().isBlank()) {
//            throw new BadRequestException("El historial no puede estar vacío");
//        }
        if (paciente.getTipo_sangre() == null || paciente.getTipo_sangre().isBlank()) {
            throw new BadRequestException("El tipo de sangre no puede estar vacío");
        }
    }


}
