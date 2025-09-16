package com.example.Pacientes.service;

import com.example.Pacientes.dto.PacienteDTO;
import com.example.Pacientes.dto.PacienteDetalleDTO;
import com.example.Pacientes.entity.PacienteEntity;
import com.example.Pacientes.exception.BadRequestException;
import com.example.Pacientes.exception.ResourceNotFoundException;
import com.example.Pacientes.reposity.PacienteReposity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PacienteServiceTest {

    @Mock //Crear objeto simulado
    private PacienteReposity pacienteReposity;

    @InjectMocks
    private PacienteService pacienteService;

    @BeforeEach//indica metdodo ejecuta antes de la prueba
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarPacientes_cuandoExistenPacientes_devuelveListaDTOs() {
        // Arrange preparar datos simulados
        PacienteEntity paciente1 = new PacienteEntity("Juan", 30, "Historial1", "O+");
        paciente1.setId(1L);
        PacienteEntity paciente2 = new PacienteEntity("Maria", 25, "Historial2", "A-");
        paciente2.setId(2L);

        List<PacienteEntity> listaSimulada = Arrays.asList(paciente1, paciente2);

        when(pacienteReposity.findAll()).thenReturn(listaSimulada);

        // Act ejecutar el metodo que probamos
        List<PacienteDTO> resultado = pacienteService.listarPacientes();

        // Assert verificar el resultado
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("Maria", resultado.get(1).getNombre());

        verify(pacienteReposity, times(1)).findAll(); // verificar que llamó al repositorio
    }

    @Test
    void obtenerPacientePorId_cuandoExiste_devuelveDTO() {
        // Arrange
        Long id = 1L;
        PacienteEntity entity = new PacienteEntity();
        entity.setId(id);
        entity.setNombre("Juan");
        entity.setEdad(30);
        entity.setHistorial("Historial1");
        entity.setTipo_sangre("O+");

        when(pacienteReposity.findById(id)).thenReturn(Optional.of(entity));

        // Act
        PacienteDTO resultado = pacienteService.obtenerPacientePorId(id);

        // Assert
        assertNotNull(resultado);//no sea nulo
        assertEquals("Juan", resultado.getNombre());
        assertEquals(30, resultado.getEdad());
        assertEquals("O+", resultado.getTipoSangre());

        verify(pacienteReposity, times(1)).findById(id);
    }

    @Test
    void obtenerPacientePorId_cuandoNoExiste_lanzaExcepcion() {
        // Arrange
        Long id = 99L;
        when(pacienteReposity.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class,
                () -> pacienteService.obtenerPacientePorId(id));

        verify(pacienteReposity, times(1)).findById(id);
    }

    @Test
    void obtenerPacienteDetallePorId_cuandoExiste_devuelveDetalleDTO(){

        // Arrange
        Long id = 1L;
        PacienteEntity entity = new PacienteEntity();
        entity.setId(id);
        entity.setNombre("Lucas");
        entity.setEdad(28);
        entity.setHistorial("Operacion");
        entity.setTipo_sangre("A+");

        when(pacienteReposity.findById(id)).thenReturn(Optional.of(entity));

        // Act
        PacienteDetalleDTO resultado = pacienteService.obtenerPacienteDetalle(id);

        // Assert
        assertNotNull(resultado);//no sea nulo
        assertEquals("Lucas", resultado.getNombre());
        assertEquals(28, resultado.getEdad());
        assertEquals("Operacion",resultado.getHistorial());
        assertEquals("A+", resultado.getTipo_sangre());

        verify(pacienteReposity, times(1)).findById(id);

    }

    @Test
    void obtenerPacienteDetallePorId_cuandoNoExiste_lanzaExcepcion() {
        // Arrange
        Long id = 99L;
        when(pacienteReposity.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class,
                () -> pacienteService.obtenerPacienteDetalle(id));

        verify(pacienteReposity, times(1)).findById(id);
    }

    @Test
    void crearPAciente_cuandoExsite_devuelveDTO(){

        // Arrange
        PacienteDTO dto = new PacienteDTO(null, "Ana", 22, "B+"); // DTO sin ID
        PacienteEntity entity = new PacienteEntity("Ana", 22, "Sin historial", "B+");
        entity.setId(1L); // Simulamos que al guardar la BD le asigna un ID

        when(pacienteReposity.save(any(PacienteEntity.class))).thenReturn(entity);

        // Act
        PacienteDTO resultado = pacienteService.guardarPaciente(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Ana", resultado.getNombre());
        assertEquals(22, resultado.getEdad());
        assertEquals("B+", resultado.getTipoSangre());

        verify(pacienteReposity, times(1)).save(any(PacienteEntity.class));

    }

    @Test
    void guardarPaciente_cuandoNombreEsVacio_lanzaExcepcion() {
        // Arrange
        PacienteDTO dto = new PacienteDTO(null, "", 25, "O+");

        // Act + Assert
        assertThrows(BadRequestException.class,
                () -> pacienteService.guardarPaciente(dto));

        verify(pacienteReposity, never()).save(any());
    }

    @Test
    void guardarPaciente_cuandoEdadEsNegativa_lanzaExcepcion() {
        // Arrange
        PacienteDTO dto = new PacienteDTO(null, "Carlos", -5, "A+");

        // Act + Assert
        assertThrows(BadRequestException.class,
                () -> pacienteService.guardarPaciente(dto));

        verify(pacienteReposity, never()).save(any());
    }


    @Test
    void guardarPaciente_cuandoTipoSangreEsVacio_lanzaExcepcion() {
        // Arrange
        PacienteDTO dto = new PacienteDTO(null, "Pedro", 33, "");

        // Act + Assert
        assertThrows(BadRequestException.class,
                () -> pacienteService.guardarPaciente(dto));

        verify(pacienteReposity, never()).save(any());
    }


    @Test
    void actualizarHistorial_cuandoPacienteExiste_actualizaYDevuelveDetalleDTO() {
        // Arrange
        Long id = 1L;
        PacienteEntity entity = new PacienteEntity("Ana", 30, "Sin historial", "A+");
        entity.setId(id);

        when(pacienteReposity.findById(id)).thenReturn(Optional.of(entity));
        when(pacienteReposity.save(any(PacienteEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String nuevoHistorial = "Paciente con operación en 2023";

        // Act
        PacienteDetalleDTO resultado = pacienteService.actualizarHistorial(id, nuevoHistorial);

        // Assert
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Ana", resultado.getNombre());
        assertEquals(30, resultado.getEdad());
        assertEquals("A+", resultado.getTipo_sangre());
        assertEquals(nuevoHistorial, resultado.getHistorial());

        verify(pacienteReposity).findById(id);
        verify(pacienteReposity).save(entity);
    }

    @Test
    void cambiarPaciente_cuandoPacienteExiste_actualizaYDevuelveDTO() {
        // Arrange
        Long id = 1L;
        PacienteEntity existente = new PacienteEntity("Juan", 40, "Historial viejo", "O+");
        existente.setId(id);

        PacienteDTO dtoActualizado = new PacienteDTO(null, "Pedro", 35, "A+");

        when(pacienteReposity.findById(id)).thenReturn(Optional.of(existente));
        when(pacienteReposity.save(any(PacienteEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        PacienteDTO resultado = pacienteService.cambiarPaciente(id, dtoActualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());         // el id debe mantenerse
        assertEquals("Pedro", resultado.getNombre());
        assertEquals(35, resultado.getEdad());
        assertEquals("A+", resultado.getTipoSangre());

        verify(pacienteReposity).findById(id);
        verify(pacienteReposity).save(existente);
    }

    @Test
    void cambiarPaciente_cuandoPacienteNoExiste_lanzaExcepcion() {
        // Arrange
        Long id = 99L;
        PacienteDTO dto = new PacienteDTO(null, "Luis", 28, "B+");

        when(pacienteReposity.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class,
                () -> pacienteService.cambiarPaciente(id, dto));

        verify(pacienteReposity, never()).save(any());
    }


    @Test
    void eliminarPAciente_cuandoExiste_elimina(){

        Long id = 1L;

        // Arrange simulamos el paciente existe
        when(pacienteReposity.existsById(id)).thenReturn(true);


        // Act
        pacienteService.eliminarPaciente(id);

        // Assert
        verify(pacienteReposity, times(1)).deleteById(id);
    }

    @Test
    void eliminarPaciente_cuandoNoExiste_LanzaExcepcion() {
        // Arrange
        Long id = 99L;
        when(pacienteReposity.existsById(id)).thenReturn(false);

        // Act
        Executable accion = new Executable() {
            @Override
            public void execute() {
                pacienteService.eliminarPaciente(id);
            }
        };
        // Assert
        assertThrows(ResourceNotFoundException.class, accion);

        verify(pacienteReposity, never()).deleteById(id);
    }


}
