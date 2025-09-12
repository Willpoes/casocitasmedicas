package com.example.Pacientes.controller;

import com.example.Pacientes.entity.PacienteEntity;
import com.example.Pacientes.service.PacienteService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<PacienteEntity> obtenerPacientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPacientePorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<PacienteEntity> crearPaciente(@RequestBody PacienteEntity paciente){
        PacienteEntity nuevoPaciente = pacienteService.guardarPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PacienteEntity> actualizarPaciente(@PathVariable Long id, @RequestBody PacienteEntity paciente){
        // Si no existe, el service lanza PacienteNotFoundException
        PacienteEntity actualizado = pacienteService.cambiarPaciente(id, paciente);
        return ResponseEntity.ok(actualizado);

    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> borrarPaciente(@PathVariable Long id){

        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }


}
