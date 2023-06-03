package com.springbootcrud.springboot.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "Datos de la reserva")
public class ReservaRequest {
    @ApiModelProperty(value = "ID de la reserva", example = "1")
    private Long id;
    @ApiModelProperty(value = "ID de la habitación a reservar", example = "1", required = true)
    private Long habitacionId;
    @ApiModelProperty(value = "Contacto", example = "8995569", required = true)
    private String contacto;

    @ApiModelProperty(value = "Hora de la reserva", example = "13:30", required = true)
    private String hora;
    @ApiModelProperty(value = "Nombre w", example = "John Doe", required = true)
    private String nombre;
    @ApiModelProperty(value = "Número de personas", example = "2", required = true)
    private int numeroPersonas;

    // Nuevas propiedades para la fecha de entrada y salida
    @ApiModelProperty(value = "Fecha de entrada", example = "2023-06-01", required = true)
    private String fechaEntrada;

    @ApiModelProperty(value = "Fecha de salida", example = "2023-06-03", required = true)
    private String fechaSalida;


    // Getters y setters

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHabitacionId() {
        return habitacionId;
    }

    public void setHabitacionId(Long habitacionId) {
        this.habitacionId = habitacionId;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }



    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }
}

