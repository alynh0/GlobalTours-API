package com.globaltours.agencia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globaltours.agencia.model.Comentario;
import com.globaltours.agencia.repository.ComentarioRepository;

@Service
public class ComentarioService {
    
    @Autowired
    ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    public List<Comentario> listarComentarios() {
        return comentarioRepository.findAll();
    }

    public Optional<Comentario> buscarComentario(Long id) {
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        if (comentario.isPresent()) {
            return comentario;
        } else {
            return null;
        }
    }

    public Comentario salvarComentario(Comentario comentario) {
        try {
            return comentarioRepository.save(comentario);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar comentario", e);
        }
    }

    public Comentario atualizarComentario(Comentario comentario) {
        try {
            return comentarioRepository.save(comentario);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar comentario", e);
        }
    }

    public void deletarComentario(Long id) {
        try {
            comentarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar comentario", e);
        }
    }

    public List<Comentario> listarComentariosPorViagem(Long id) {
        return comentarioRepository.findByViagemId(id);
    }

}
