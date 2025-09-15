package com.example.Pacientes.dto;

public class PacienteDTO {

    private Long id;
    private String nombre;
    private int edad;
    private String tipoSangre;

    public PacienteDTO(){
    }

    public PacienteDTO(Long id, String nombre, int edad, String tipoSangre) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.tipoSangre = tipoSangre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getTipoSangre() { return tipoSangre; }
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }

}
