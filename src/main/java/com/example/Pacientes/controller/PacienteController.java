package com.example.Pacientes.controller;

import com.example.Pacientes.dto.HistorialRequest;
import com.example.Pacientes.dto.PacienteDTO;
import com.example.Pacientes.dto.PacienteDetalleDTO;
import com.example.Pacientes.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> obtenerPacientes() {
        List<PacienteDTO> pacientes = pacienteService.listarPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> obtenerPacientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPacientePorId(id));
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<PacienteDetalleDTO> obtenerPacienteDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPacienteDetalle(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<PacienteDTO> crearPaciente(@RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO nuevoPaciente = pacienteService.guardarPaciente(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }

    //  Actualizar solo el historial
    @PutMapping("/historial/{id}")
    public PacienteDetalleDTO actualizarHistorial(
            @PathVariable Long id,
            @RequestBody HistorialRequest request) {
        return pacienteService.actualizarHistorial(id, request.getHistorial());
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PacienteDTO> actualizarPaciente(
            @PathVariable Long id,
            @RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO actualizado = pacienteService.cambiarPaciente(id, pacienteDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> borrarPaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
