package com.example.Pacientes.reposity;

import com.example.Pacientes.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteReposity extends JpaRepository<PacienteEntity, Long> {


}
