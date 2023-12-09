package com.globaltours.agencia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globaltours.agencia.model.Comentario;
import com.globaltours.agencia.model.Viagem;
import com.globaltours.agencia.repository.ComentarioRepository;
import com.globaltours.agencia.repository.ViagemRepository;

@Service
public class ViagemService {
    
    @Autowired
    ViagemRepository viagemRepository;

    @Autowired
    ComentarioRepository comentarioRepository;

    public ViagemService(ViagemRepository viagemRepository) {
        this.viagemRepository = viagemRepository;
    }

    public List<Viagem> listarViagens() {
        return viagemRepository.findAll();
    }

    public Optional<Viagem> buscarViagem(Long id) {
        Optional<Viagem> viagem = viagemRepository.findById(id);
        if (viagem.isPresent()) {
            return viagem;
        } else {
            return null;
        }
    }

    public Viagem salvarViagem(Viagem viagem) {
        try {
            return viagemRepository.save(viagem);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar viagem", e);
        }
    }

    public Viagem atualizarViagem(Long id, Viagem viagem) {
        try {
            Optional<Viagem> viagemAtual = viagemRepository.findById(id);
            if (viagemAtual.isPresent()) {
                viagemAtual.get().setOrigem(viagem.getOrigem());
                viagemAtual.get().setDestino(viagem.getDestino());
                viagemAtual.get().setDataIda(viagem.getDataIda());
                viagemAtual.get().setDataVolta(viagem.getDataVolta());
                viagemAtual.get().setPreco(viagem.getPreco());
                viagemAtual.get().setDescricao(viagem.getDescricao());
                viagemRepository.save(viagemAtual.get());
                return viagemAtual.get();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar viagem", e);
        }
    }

    public void deletarViagem(Long id) {
        try {
            viagemRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar viagem", e);
        }
    }

    public List<Comentario> listarComentarios(Long id) {
        Optional<Viagem> viagem = viagemRepository.findById(id);
        if (viagem.isPresent()) {
            return viagem.get().getComentarios();
        } else {
            return null;
        }
    }

}
