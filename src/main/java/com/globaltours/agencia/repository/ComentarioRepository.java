package com.globaltours.agencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globaltours.agencia.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    
    List<Comentario> findByViagemId(Long id);

}
