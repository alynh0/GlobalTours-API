package com.globaltours.agencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globaltours.agencia.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
}
