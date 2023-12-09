package com.globaltours.agencia.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globaltours.agencia.model.Cliente;
import com.globaltours.agencia.model.Comentario;
import com.globaltours.agencia.repository.ClienteRepository;
import com.globaltours.agencia.repository.ComentarioRepository;

@Service
public class ClienteService {
    
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ComentarioRepository comentarioRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarCliente(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return cliente;
        } else {
            return null;
        }
    }

    public Cliente salvarCliente(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente", e);
        }
    }

    public Cliente atualizarCliente(Long id, Cliente cliente) {
        try {
            Optional<Cliente> clienteAtual = clienteRepository.findById(id);
            if (clienteAtual.isPresent()) {
                clienteAtual.get().setNome(cliente.getNome());
                clienteAtual.get().setCpf(cliente.getCpf());
                clienteAtual.get().setEmail(cliente.getEmail());
                clienteAtual.get().setPassword(cliente.getPassword());
                clienteAtual.get().setTelefone(cliente.getTelefone());
                clienteRepository.save(clienteAtual.get());
                return clienteAtual.get();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cliente", e);
        }
    }

    public void deletarCliente(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar cliente", e);
        }
    }

    public Comentario publicarComentario(Long id, Comentario comentario) {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(id);
            if (cliente.isPresent()) {
                comentario.setCliente(cliente.get());
                return comentarioRepository.save(comentario);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao publicar comentário", e);
        }
    }

    public void deletarComentario(Long id) {
        try {
            comentarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar comentário", e);
        }
    }

    public Comentario atualizarComentario(Long id, Comentario comentario) {
        try {
            Optional<Comentario> comentarioAtual = comentarioRepository.findById(id);
            if (comentarioAtual.isPresent()) {
                comentarioAtual.get().setTexto(comentario.getTexto());
                return comentarioRepository.save(comentarioAtual.get());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar comentário", e);
        }
    }

}
