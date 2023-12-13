package com.globaltours.agencia.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "viagem_id")
    @JsonIgnore
    private Viagem viagem;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataReserva;

    public Reserva() {
    }

    public Reserva(Cliente cliente, Viagem viagem, LocalDate dataReserva) {
        validarDataReserva(dataReserva, viagem.getDataIda(), viagem.getDataVolta());

        this.cliente = cliente;
        this.viagem = viagem;
        this.dataReserva = dataReserva;
    }

    public Reserva(Viagem viagem, LocalDate dataReserva) {
        validarDataReserva(dataReserva, viagem.getDataIda(), viagem.getDataVolta());

        this.viagem = viagem;
        this.dataReserva = dataReserva;
    }

    // Valida se a data da reserva está entre a data de início e fim da viagem
    private void validarDataReserva(LocalDate dataReserva, LocalDate dataIda, LocalDate dataVolta) {
        if (dataReserva.isBefore(dataIda.minusDays(1))) {
            throw new IllegalArgumentException("A data da reserva deve ser anterior à data de ida da viagem");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }
}
