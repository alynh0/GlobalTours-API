package com.globaltours.agencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globaltours.agencia.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
