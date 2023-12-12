package com.globaltours.agencia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globaltours.agencia.model.Reserva;
import com.globaltours.agencia.repository.ReservaRepository;

@Service
public class ReservaService {
    
    @Autowired
    ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarReserva(Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isPresent()) {
            return reserva;
        } else {
            return null;
        }
    }

    public Reserva salvarReserva(Reserva reserva) {
        try {
            return reservaRepository.save(reserva);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar reserva", e);
        }
    }

    public Reserva atualizarReserva(Reserva reserva) {
        try {
            return reservaRepository.save(reserva);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar reserva", e);
        }
    }

    public void deletarReserva(Long id) {
        try {
            reservaRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar reserva", e);
        }
    }

    public List<Reserva> listarReservasPorViagem(Long id) {
        return reservaRepository.findByViagemId(id);
    }

    public List<Reserva> listarReservasPorCliente(Long id) {
        return reservaRepository.findByClienteId(id);
    }

}
