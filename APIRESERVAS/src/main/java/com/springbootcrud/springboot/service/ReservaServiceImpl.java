package com.springbootcrud.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootcrud.springboot.dao.ReservaRepository;
import com.springbootcrud.springboot.entity.Reserva;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaServiceImpl(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public List<Reserva> findAll() {
        return (List<Reserva>) reservaRepository.findAll();
    }

    @Override
    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public void createReserva(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    @Override
    public void modifyReserva(Long id, Reserva reserva) {
        Optional<Reserva> existingReserva = reservaRepository.findById(id);
        if (existingReserva.isPresent()) {
            Reserva updatedReserva = existingReserva.get();
            updatedReserva.setNumeroPersonas(reserva.getNumeroPersonas());
            updatedReserva.setNombre(reserva.getNombre());
            updatedReserva.setContacto(reserva.getContacto());
            updatedReserva.setFechaEntrada(reserva.getFechaEntrada());
            updatedReserva.setFechaSalida(reserva.getFechaSalida());
            reservaRepository.save(updatedReserva);
        }
    }

    @Override
    public void cancelReserva(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
        }
    }

}