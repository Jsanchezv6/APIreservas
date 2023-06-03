package com.springbootcrud.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootcrud.springboot.entity.Habitacion;
import com.springbootcrud.springboot.dao.HabitacionRepository;

@Service
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;

    @Autowired
    public HabitacionServiceImpl(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    @Override
    public List<Habitacion> findAll() {
        return (List<Habitacion>) habitacionRepository.findAll();
    }

    @Override
    public Optional<Habitacion> findById(Long id) {
        return habitacionRepository.findById(id);
    }

    @Override
    public void createHabitacion(Habitacion habitacion) {
        habitacionRepository.save(habitacion);
    }

    @Override
    public void modifyHabitacion(Long id, Habitacion habitacion) {
        Optional<Habitacion> existingHabitacion = habitacionRepository.findById(id);
        if (existingHabitacion.isPresent()) {
            Habitacion updatedHabitacion = existingHabitacion.get();
            updatedHabitacion.setNumeroHabitacion(habitacion.getNumeroHabitacion());
            updatedHabitacion.setPrecio(habitacion.getPrecio());
            updatedHabitacion.setTipo(habitacion.getTipo());
            updatedHabitacion.setDisponible(habitacion.isDisponible());
            habitacionRepository.save(updatedHabitacion);
        }
    }

    @Override
    public void deleteHabitacion(Long id) {
        if (habitacionRepository.existsById(id)) {
            habitacionRepository.deleteById(id);
        }
    }

    @Override
    public List<Habitacion> findByDisponibleTrue() {
        return habitacionRepository.findByDisponibleTrue();
    }
}
