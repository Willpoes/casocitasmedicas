package com.example.Pacientes.controller;

import com.example.Pacientes.entity.PacienteEntity;
import com.example.Pacientes.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;


    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<PacienteEntity>> obtenerPacientes() {
        List<PacienteEntity> pacientes = pacienteService.listarPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @PostMapping("/crear")
    public PacienteEntity crearPaciente(@RequestBody PacienteEntity paciente){
        return pacienteService.guardarPaciente(paciente);
    }

    @PutMapping("/actualizar/{id}")
    public PacienteEntity actualizarPaciente(@PathVariable Long id, @RequestBody PacienteEntity paciente){
        return pacienteService.cambiarPaciente(id, paciente);

    }

    @DeleteMapping("/eliminar/{id}")
    public void borrarPaciente(@PathVariable Long id){
        pacienteService.eliminarPaciente(id);
    }




}
