package com.globaltours.agencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globaltours.agencia.model.Viagem;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {
    
}
