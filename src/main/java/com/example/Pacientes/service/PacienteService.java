package com.example.Pacientes.service;

import com.example.Pacientes.entity.PacienteEntity;
import com.example.Pacientes.reposity.PacienteReposity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteReposity pacienteReposity;

    public PacienteService(PacienteReposity pacienteReposity) {
        this.pacienteReposity = pacienteReposity;
    }

    public List<PacienteEntity> listarPacientes(){
        return pacienteReposity.findAll();
    }

    public PacienteEntity guardarPaciente(PacienteEntity paciente){
        return pacienteReposity.save(paciente);
    }

    public PacienteEntity cambiarPaciente(Long id, PacienteEntity paciente){
        PacienteEntity nuevo = pacienteReposity.findById(id).orElse(null);

        if(nuevo != null){
            nuevo.setNombre(paciente.getNombre());
            nuevo.setEdad(paciente.getEdad());
            nuevo.setHistorial(paciente.getHistorial());
            return pacienteReposity.save(nuevo);
        }
        return null;

    }

    public void eliminarPaciente(Long id){
        pacienteReposity.deleteById(id);
    }


}
