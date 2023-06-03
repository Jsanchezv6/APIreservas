package com.springbootcrud.springboot.dao;

import com.springbootcrud.springboot.entity.Habitacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends CrudRepository<Habitacion, Long> {
    List<Habitacion> findByDisponibleTrue();

}
