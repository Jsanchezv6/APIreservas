package com.springbootcrud.springboot.service;

import java.util.List;
import java.util.Optional;

import com.springbootcrud.springboot.entity.Habitacion;

public interface HabitacionService {

    List<Habitacion> findAll();

    Optional<Habitacion> findById(Long id);

    void createHabitacion(Habitacion habitacion);

    void modifyHabitacion(Long id, Habitacion habitacion);

    void deleteHabitacion(Long id);

    List<Habitacion> findByDisponibleTrue();
}
