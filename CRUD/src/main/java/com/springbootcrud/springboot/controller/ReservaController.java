package com.springbootcrud.springboot.controller;

import com.springbootcrud.springboot.entity.Reserva;
import com.springbootcrud.springboot.exception.ResourceNotFoundException;
import com.springbootcrud.springboot.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.springbootcrud.springboot.dao.HabitacionRepository;
import java.util.List;
import java.util.Optional;

import com.springbootcrud.springboot.entity.Habitacion;
import com.springbootcrud.springboot.entity.ReservaRequest;
import com.springbootcrud.springboot.dao.ReservaRepository;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private ReservaRepository reservaRepository;

    private final ReservaService reservaService;
    private final HabitacionRepository habitacionRepository;

    @Autowired
    public ReservaController(ReservaService reservaService, HabitacionRepository habitacionRepository) {
        this.reservaService = reservaService;
        this.habitacionRepository = habitacionRepository;
    }

    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Reserva> obtenerReserva(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.findById(id);
        if (reserva.isPresent()) {
            return reserva;
        } else {
            throw new ResourceNotFoundException("Reserva no encontrada con ID: " + id);
        }
    }


    @PostMapping
    @ApiOperation(value = "Crear una reserva")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reserva creada exitosamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida"),
            @ApiResponse(code = 404, message = "Habitación no encontrada")
    })
    public ResponseEntity<String> crearReserva(
            @ApiParam(value = "Datos de la reserva", required = true)
            @RequestBody ReservaRequest reservaRequest) {
        try {
            // Obtener el ID de la habitación desde la solicitud y buscar la habitación en la base de datos
            Habitacion habitacion = habitacionRepository.findById(reservaRequest.getHabitacionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada"));

            // Verificar si la habitación está disponible
            if (!habitacion.isDisponible()) {
                throw new ResourceNotFoundException("Habitación no disponible");
            }

            // Crear una instancia de Reserva y establecer los datos de la solicitud
            Reserva reserva = new Reserva();
            reserva.setContacto(reservaRequest.getContacto());
            reserva.setFechaEntrada(reservaRequest.getFechaEntrada());
            reserva.setFechaSalida(reservaRequest.getFechaSalida());
            reserva.setNombre(reservaRequest.getNombre());
            reserva.setNumeroPersonas(reservaRequest.getNumeroPersonas());
            reserva.setHora(reservaRequest.getHora());
            reserva.setHabitacion(habitacion);

            // Guardar la reserva en la base de datos
            reservaRepository.save(reserva);

            // Actualizar la disponibilidad de la habitación
            habitacion.setDisponible(false);
            habitacionRepository.save(habitacion);

            return ResponseEntity.ok("Reserva creada exitosamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solicitud inválida.");
        }
    }

    @PutMapping("/{id}")
    public void modificarReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Optional<Reserva> reservaExistente = reservaService.findById(id);
        if (reservaExistente.isPresent()) {
            reservaService.modifyReserva(id, reserva);
        } else {
            throw new ResourceNotFoundException("Reserva no encontrada con ID: " + id);
        }
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelarReserva(@PathVariable Long id) {
        try {
            Reserva reserva = reservaService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
            Habitacion habitacion = habitacionRepository.findById(reserva.getHabitacion().getId()).orElseThrow(() -> new ResourceNotFoundException("Habitacion no encontrada"));
            reservaService.cancelReserva(id);
            habitacion.setDisponible(true);
            habitacionRepository.save(habitacion);

            return ResponseEntity.ok("Reserva cancelada correctamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/habitaciones/disponibles")
    public List<Habitacion> obtenerHabitacionesDisponibles() {
        return habitacionRepository.findByDisponibleTrue();
    }
}
