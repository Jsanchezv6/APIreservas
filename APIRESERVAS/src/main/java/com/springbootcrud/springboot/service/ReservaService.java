package com.springbootcrud.springboot.service;

import com.springbootcrud.springboot.entity.Reserva;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservaService {

    List<Reserva> findAll();

    Optional<Reserva> findById(Long id);

    void createReserva(Reserva reserva);

    void modifyReserva(Long id, Reserva reserva);

    void cancelReserva(Long id);


}