package com.example.Pacientes.service;

import com.example.Pacientes.entity.PacienteEntity;
import com.example.Pacientes.exception.BadRequestException;
import com.example.Pacientes.exception.ResourceNotFoundException;
import com.example.Pacientes.reposity.PacienteReposity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteReposity pacienteReposity;

    public PacienteService(PacienteReposity pacienteReposity) {
        this.pacienteReposity = pacienteReposity;
    }

    public List<PacienteEntity> listarPacientes(){
        return pacienteReposity.findAll();
    }

    public PacienteEntity obtenerPacientePorId(Long id){
        return pacienteReposity.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente con ID " + id + " no encontrado"));
    }

    //Crear PAciente
    public PacienteEntity guardarPaciente(PacienteEntity paciente){

        if (paciente.getNombre() == null || paciente.getNombre().isBlank()){
            throw new BadRequestException("El nombre no puede esta vacio");
        }
        if (paciente.getEdad()<0){
            throw new BadRequestException("La edad no puede ser negativa");
        }
        if (paciente.getHistorial() == null || paciente.getHistorial().isBlank()) {
            throw new BadRequestException("El historial no puede esta vacio");
        }

            return pacienteReposity.save(paciente);
    }

    //actualizar Paciente
    public PacienteEntity cambiarPaciente(Long id, PacienteEntity paciente){

        if (paciente.getNombre() == null || paciente.getNombre().isBlank()) {
            throw new BadRequestException("El nombre del paciente no puede estar vacío");
        }
        if (paciente.getEdad()<0){
            throw new BadRequestException("La edad no puede ser negativa");
        }
        if (paciente.getHistorial() == null || paciente.getHistorial().isBlank()) {
            throw new BadRequestException("El historial no puede esta vacio");
        }

        // buscar paciente
        Optional<PacienteEntity> optionalPaciente = pacienteReposity.findById(id);

        if (optionalPaciente.isEmpty()) {
            throw new ResourceNotFoundException("No se puede actualizar, el paciente con ID " + id + " no existe");
        }

        PacienteEntity pacienteExistente = optionalPaciente.get();
        pacienteExistente.setNombre(paciente.getNombre());
        pacienteExistente.setEdad(paciente.getEdad());
        pacienteExistente.setHistorial(paciente.getHistorial());

        return pacienteReposity.save(pacienteExistente);

    }

    //borrar paciente
    public void eliminarPaciente(Long id){
        if (!pacienteReposity.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, el paciente con ID " + id + " no existe");
        }
        pacienteReposity.deleteById(id);
    }


}
