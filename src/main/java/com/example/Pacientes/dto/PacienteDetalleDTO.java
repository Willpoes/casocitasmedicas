package com.example.Pacientes.dto;

public class PacienteDetalleDTO {

    private Long id;
    private String nombre;
    private int edad;
    private String historial;
    private String tipo_sangre;

    public PacienteDetalleDTO() {

    }

    public PacienteDetalleDTO(Long id, String nombre, int edad, String historial, String tipo_sangre) {
        this.id = id;
        this.edad = edad;
        this.nombre = nombre;
        this.historial = historial;
        this.tipo_sangre = tipo_sangre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public String getTipo_sangre() {
        return tipo_sangre;
    }

    public void setTipo_sangre(String tipo_sangre) {
        this.tipo_sangre = tipo_sangre;
    }
}
