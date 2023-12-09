package com.globaltours.agencia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globaltours.agencia.model.Cliente;
import com.globaltours.agencia.model.Comentario;
import com.globaltours.agencia.model.Viagem;
import com.globaltours.agencia.service.ClienteService;
import com.globaltours.agencia.service.ViagemService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/cliente")
public class ClienteController {
    
    @Autowired
    ClienteService clienteService;

    @Autowired
    ViagemService viagemService;

    @GetMapping("/listar")
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Long id) {
        try {
            Optional<Cliente> cliente = clienteService.buscarCliente(id);
            // CASO O CLIENTE EXISTA, RETORNA O STATUS 200 + O CLIENTE
            return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            // CASO OCORRA ALGUM ERRO, RETORNA O STATUS 400 + MENSAGEM DE ERRO
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/criar-cliente")
    public ResponseEntity<?> criarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente novoCliente = clienteService.salvarCliente(cliente);
            // CASO O CLIENTE SEJA CRIADO COM SUCESSO, RETORNA O STATUS 201 + O CLIENTE CRIADO
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
        } catch (Exception e) {
            // CASO OCORRA ALGUM ERRO, RETORNA O STATUS 500 + MENSAGEM DE ERRO
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/atualizar-cliente")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {

        Optional<Cliente> clienteEncontrado = clienteService.buscarCliente(id);
        if (clienteEncontrado.isPresent()) {
            return ResponseEntity.ok(clienteService.atualizarCliente(id, cliente));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        try {
            clienteService.deletarCliente(id);
            // CASO O CLIENTE SEJA DELETADO COM SUCESSO, RETORNA O STATUS 200
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // CASO OCORRA ALGUM ERRO, RETORNA O STATUS 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/comentarios")
    public ResponseEntity<?> listarComentarios(@PathVariable Long id, @RequestParam(required = false) Long viagemID) {

        Optional<Cliente> cliente = clienteService.buscarCliente(id);
        if (cliente.isPresent()) {
            if (viagemID != null) {
                Optional<Viagem> viagem = viagemService.buscarViagem(viagemID);
                if (viagem.isPresent()) {
                    return ResponseEntity.ok(viagem.get().getComentarios());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viagem não encontrada");
                }
            } else {
                return ResponseEntity.ok(cliente.get().getComentarios());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

    }

    @PostMapping("/{id}/comentar")
    public ResponseEntity<?> adicionarComentario(@PathVariable Long clienteID, @RequestBody Comentario comentario) {

        Optional<Cliente> cliente = clienteService.buscarCliente(clienteID);
        if (cliente.isPresent()) {
            Optional<Viagem> viagem = viagemService.buscarViagem(comentario.getViagem().getId());
            if (viagem.isPresent()) {
                Comentario novoComentario = clienteService.publicarComentario(clienteID, comentario);
                cliente.get().getComentarios().add(novoComentario);
                viagem.get().getComentarios().add(novoComentario);
                viagemService.atualizarViagem(viagem.get().getId(), viagem.get());
                clienteService.atualizarCliente(clienteID, cliente.get()); // alterada
                return ResponseEntity.status(HttpStatus.CREATED).body(novoComentario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viagem não encontrada");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }

}
