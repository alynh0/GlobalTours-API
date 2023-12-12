package com.globaltours.agencia.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "viagem_id")
    private Viagem viagem;

    @Column(nullable = false)
    private LocalDate dataReserva;

    public Reserva() {
    }

    public Reserva(Cliente cliente, Viagem viagem, LocalDate dataReserva) {
        validarDataReserva(dataReserva, viagem.getDataIda(), viagem.getDataVolta());

        this.cliente = cliente;
        this.viagem = viagem;
        this.dataReserva = dataReserva;
    }

    private void validarDataReserva(LocalDate dataReserva, LocalDate dataIda, LocalDate dataVolta) {
        if (dataReserva.isBefore(dataIda) || dataReserva.isAfter(dataVolta)) {
            throw new IllegalArgumentException("A data da reserva deve estar entre a data de início e fim da viagem.");
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
