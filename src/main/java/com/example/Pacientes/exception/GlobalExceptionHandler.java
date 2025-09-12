package com.example.Pacientes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


 //Con @ControllerAdvice, Spring intercepta los errores
@ControllerAdvice
public class GlobalExceptionHandler {

    // datos inválidos en una petición
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handlerBadRequest(BadRequestException badRequestException){

        // Creamos un cuerpo de respuesta en formato JSON
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());       // Fecha y hora del error
        body.put("status", HttpStatus.BAD_REQUEST.value());          // Código 400
        body.put("error", "Mala peticion");                          // Texto descriptivo
        body.put("message", badRequestException.getMessage());

        // Devolvemos la respuesta con estado 400
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


     //cuando se busca un paciente que no existe.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.NOT_FOUND.value());            // Código 404
        body.put("error", "Recurso no encontrado");                  // Texto descriptivo
        body.put("message", ex.getMessage());                        // Mensaje específico

        // Devolvemos la respuesta con estado 404
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja cualquier excepción genérica (Exception).
     * Ejemplo: NullPointerException, errores inesperados, etc.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());      // Código 500
        body.put("error", "Error inesperado");                             // Texto descriptivo
        body.put("message", ex.getMessage());                              // Mensaje específico (puede ser null)

        // Devolvemos la respuesta con estado 500
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
