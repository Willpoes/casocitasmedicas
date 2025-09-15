package com.example.Pacientes.dto;

public class HistorialRequest {
    private String historial;

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public HistorialRequest(String historial) {
        this.historial = historial;

    }
}
