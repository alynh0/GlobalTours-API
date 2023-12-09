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

import com.globaltours.agencia.model.Viagem;
import com.globaltours.agencia.service.ComentarioService;
import com.globaltours.agencia.service.ViagemService;

@RestController
@RequestMapping("/viagens")
public class ViagemController {
    
    @Autowired
    ViagemService viagemService;

    @Autowired
    ComentarioService comentarioService;

    @GetMapping("/listar")
    public List<Viagem> listarViagens() {
        return viagemService.listarViagens();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarViagemPorID(@PathVariable Long id) {
        try {
            Optional<Viagem> viagem = viagemService.buscarViagem(id);
            // CASO A VIAGEM EXISTA, RETORNA O STATUS 200 + A VIAGEM
            return viagem.isPresent() ? ResponseEntity.ok(viagem.get()) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/criar-viagem")
    public ResponseEntity<?> criarViagem(@RequestBody Viagem viagem) {
        try {
            Viagem novaViagem = viagemService.salvarViagem(viagem);
            // CASO A VIAGEM SEJA CRIADA COM SUCESSO, RETORNA O STATUS 201 + A VIAGEM CRIADA
            return ResponseEntity.status(HttpStatus.CREATED).body(novaViagem);
        } catch (Exception e) {
            // CASO OCORRA ALGUM ERRO, RETORNA O STATUS 500 + MENSAGEM DE ERRO
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("{id}/atualizar-viagem")
    public ResponseEntity<?> atualizarViagem(@PathVariable Long id, @RequestBody Viagem viagem) {

        Optional<Viagem> viagemExistente = viagemService.buscarViagem(id);
        if (viagemExistente.isPresent()) {
            return ResponseEntity.ok(viagemService.atualizarViagem(id, viagem));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarViagem(@PathVariable Long id) {
        try {
            viagemService.deletarViagem(id);
            // CASO A VIAGEM SEJA DELETADA COM SUCESSO, RETORNA O STATUS 200
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // CASO OCORRA ALGUM ERRO, RETORNA O STATUS 500 + MENSAGEM DE ERRO
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/comentarios")
    public ResponseEntity<?> listarComentarios(@PathVariable Long id) {
        try {
            Optional<Viagem> viagem = viagemService.buscarViagem(id);
            if (viagem.isPresent()) {
                // CASO A VIAGEM EXISTA, RETORNA O STATUS 200 + OS COMENTÁRIOS DA VIAGEM
                return ResponseEntity.ok(viagem.get().getComentarios());
            } else {
                // CASO A VIAGEM NÃO EXISTA, RETORNA O STATUS 404 + MENSAGEM DE ERRO
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viagem não encontrada");
            }
        } catch (Exception e) {
            // CASO OCORRA ALGUM ERRO, RETORNA O STATUS 500 + MENSAGEM DE ERRO
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
