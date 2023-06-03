package com.springbootcrud.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springbootcrud.springboot.entity.Habitacion;
import com.springbootcrud.springboot.exception.ResourceNotFoundException;
import com.springbootcrud.springboot.service.HabitacionService;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    @Autowired
    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping
    public List<Habitacion> listarHabitaciones() {
        return habitacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> obtenerHabitacion(@PathVariable Long id) {
        Optional<Habitacion> habitacion = habitacionService.findById(id);
        if (habitacion.isPresent()) {
            return ResponseEntity.ok(habitacion.get());
        } else {
            throw new ResourceNotFoundException("Habitación no encontrada con ID: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<Void> crearHabitacion(@RequestBody Habitacion habitacion) {
        habitacionService.createHabitacion(habitacion);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modificarHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        Optional<Habitacion> habitacionExistente = habitacionService.findById(id);
        if (habitacionExistente.isPresent()) {
            habitacionService.modifyHabitacion(id, habitacion);
            return ResponseEntity.ok().build();
        } else {
            throw new ResourceNotFoundException("Habitación no encontrada con ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHabitacion(@PathVariable Long id) {
        Optional<Habitacion> habitacion = habitacionService.findById(id);
        if (habitacion.isPresent()) {
            habitacionService.deleteHabitacion(id);
            return ResponseEntity.ok().build();
        } else {
            throw new ResourceNotFoundException("Habitación no encontrada con ID: " + id);
        }
    }

    @GetMapping("/disponibles")
    public List<Habitacion> obtenerHabitacionesDisponibles() {
        List<Habitacion> habitacionesDisponibles = habitacionService.findByDisponibleTrue();
        if (habitacionesDisponibles.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron habitaciones disponibles.");
        }
        return habitacionesDisponibles;
    }

    // Manejo global de excepciones
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}