package com.springbootcrud.springboot.dao;

import com.springbootcrud.springboot.entity.Reserva;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Long> {


}
